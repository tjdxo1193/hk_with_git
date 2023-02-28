package lims.api.ms.service.impl;

import lims.api.common.domain.PagingResult;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ms.dao.ItemManageDao;
import lims.api.ms.dao.MsElnCtRptFileDao;
import lims.api.ms.domain.MsElnCtRptFileKey;
import lims.api.ms.enums.ItemManage;
import lims.api.ms.enums.PItemType;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.ItemManageFileService;
import lims.api.ms.service.ItemManageService;
import lims.api.ms.vo.ItemApprSpecVO;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.MsElnCtRptFileVO;
import lims.api.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemManageServiceImpl implements ItemManageService {
    private final ItemManageDao dao;
    private final MsElnCtRptFileDao fileDao;
    private final ItemManageFileService fileService;

    @Override
    public PagingResult<ItemManageVO> findPItem(ItemManageVO param) {
        List<ItemManageVO> resultList = dao.findPItem(param);
        return new PagingResult<>(resultList);
    }

    @Override
    public List<ItemManageVO> findVersion(ItemManageVO param) {
        return dao.findVersion(param);
    }

    @Override
    public void tempSave(ItemManageVO param) {
        int result = 0;
        result += dao.updateQmPitm(param);
        result += dao.updateQmPitmInfo(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void firstSave(ItemManageVO param) {
        int result = 0;
        param.setQpSpecProcCd(SpecProgress.APPROVED.getCode());
        param.setUseVerYn('Y');

        result = dao.firstSave(param);

        if(result == 0){
            throw new NoUpdatedDataException();
        }

        if(isWrapItemType(param)){
            matchingPkgaCodeWhenWrapTest(param);
        }

        tempSave(param);
    }

    private void matchingPkgaCodeWhenWrapTest(ItemManageVO param) {

        int updateResult = 0;
        ItemManageVO ivo = Optional.ofNullable(dao.findAitmSpecIdxAndPkgaCdBySapCode(param))
                .orElse(new ItemManageVO());

        if(StringUtils.isEmpty(ivo.getPkgaCd()) || ivo.getAitmSpecIdx() == null ){
            log.info("[bug report] packaging code not found. matching fail");
        }else{
            ivo.setPlntCd(param.getPlntCd());
            ivo.setPitmCd(param.getPitmCd());
            ivo.setPitmVer(param.getPitmVer());
            updateResult += dao.updatePkgaCd(ivo);

            ivo.setPitmSpecIdx(param.getPitmSpecIdx());
            ivo.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
            updateResult += dao.updateAitmIdx(ivo);

            if(updateResult != 2){
                throw new NoUpdatedDataException();
            }
        }
    }

    @Override
    public void revision(ItemManageVO param) {
        int result = 0;

        result += dao.updateQmPitmUseVerN(param);
        result += dao.createQmPitm(param);
        result += dao.createQmPitmInfo(param);
        result += dao.createQmPitmInfoSap(param);

        param.setPitmVer(param.getPitmVer() - 1);
        result += specRevision(param);

        if (result != 5) {
            throw new NoCreatedDataException();
        }

    }

    private boolean isWrapItemType(ItemManageVO param) {
        String itemType = param.getPitmTyp();
        return itemType.equals(PItemType.PACKAGING_MATERIAL.getCode())
                || itemType.equals(PItemType.FINISHED_SET.getCode())
                || itemType.equals(PItemType.FINISHED_SINGLE.getCode());
    }

    private boolean isElnItemType(ItemManageVO param) {
        String itemType = param.getPitmTyp();
        return itemType.equals(PItemType.SEMI_MANUFACTURES_FILLING_FOAM.getCode())
                || itemType.equals(PItemType.SEMI_MANUFACTURES_OTHER_PRODUCT.getCode())
                || itemType.equals(PItemType.SEMI_MANUFACTURES_BULK.getCode())
                || itemType.equals(PItemType.SEMI_MANUFACTURES_BASE.getCode());
    }

    public int specRevision(ItemManageVO param) {
        ItemApprSpecVO itemSpecVO = Optional.ofNullable(dao.findOldSpecInfo(param))
                .orElseThrow(() -> new IllegalArgumentException("No previous specifications found. vo is empty."));
        Integer oldAItemSpecIdx = Optional.ofNullable(itemSpecVO.getOldAitmSpecIdx())
                .orElseThrow(() -> new IllegalArgumentException(String.format("oldAItemSpecIdx => %s , is Invalid value.", itemSpecVO.getOldAitmSpecIdx())));
        String specProcCode = Optional.ofNullable(itemSpecVO.getSpecProcCd())
                .orElseThrow(() -> new IllegalArgumentException(String.format("specProcCode => %s , is Invalid value.", itemSpecVO.getSpecProcCd())));

        if (oldAItemSpecIdx == 0 || specProcCode.equals(SpecProgress.TEMPORARY_STORAGE.getCode())){
            throw new IllegalArgumentException(String.format("specProcCode => %s , oldAItemSpecIdx => %s, Data not suitable for revision", itemSpecVO.getSpecProcCd(), itemSpecVO.getOldAitmSpecIdx()));
        }

        itemSpecVO.setDelYn("N");
        itemSpecVO.setUseVerYn("N");
        itemSpecVO.setPlntCd(param.getPlntCd());
        itemSpecVO.setPitmCd(param.getPitmCd());
        itemSpecVO.setPitmVer(param.getPitmVer() + 1);
        itemSpecVO.setAitmSpecIdx(null);
        itemSpecVO.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());

        if (specProcCode.equals(SpecProgress.APPROVED.getCode())){
            dao.updateOldSpecUseVerN(itemSpecVO);
            if(!isWrapItemType(param) || !isElnItemType(param)){
                itemSpecVO.setAitmSpecIdx(itemSpecVO.getOldAitmSpecIdx());
            }
        }else{
            log.info("[bug report] param is not specApproved code. code: {}.", specProcCode);
        }

        return dao.createQmPitmSpec(itemSpecVO);
    }

    public List<MsElnCtRptFileVO> getFileList(MsElnCtRptFileVO param) {
        return fileService.getMsElnCtRptFileList(param);
    }

    public String savedFile(ItemManageVO param) {
        String ctId = param.getCtId();
        if(ctId == null || ctId.equals("0")) {
            MsElnCtRptFileKey fileKey = fileService.save(param.getAddedFiles());
            param.setCtId(fileKey.getCtId());
        } else {
            if(param.getRemovedFileIds() != null && param.getRemovedFileIds().size() > 0) {
                for (MsElnCtRptFileKey removedFileId : param.getRemovedFileIds()) {
                    fileService.deleteFile(removedFileId);
                }
            }
            fileService.save(param.getAddedFiles(), param.getCtId());
        }

        int result = dao.saveCtrptNo(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        return param.getCtId();
    }

    public List<ItemManageVO> getBomList(ItemManageVO param) {
        return dao.getBomList(param);
    }
}