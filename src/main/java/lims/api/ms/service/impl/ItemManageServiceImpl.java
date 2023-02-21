package lims.api.ms.service.impl;

import lims.api.common.domain.PagingResult;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ms.dao.ItemManageDao;
import lims.api.ms.dao.MsElnCtRptFileDao;
import lims.api.ms.domain.MsElnCtRptFileKey;
import lims.api.ms.enums.PItemType;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.ItemManageFileService;
import lims.api.ms.service.ItemManageService;
import lims.api.ms.vo.ItemApprSpecVO;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.MsElnCtRptFileVO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        if(isWrap(param)){
            param.setSpecProcCd(SpecProgress.APPROVED.getCode());
            param.setAitmSpecIdx(dao.findAItemSpecIdxBySapCode(param));
            if(param.getAitmSpecIdx() != null){
                result += dao.updateSpecNewAitemIdxBySapCode(param);
            }
        }

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void firstSave(ItemManageVO request) {
        int result = 0;

        request.setQpSpecProcCd(SpecProgress.APPROVED.getCode());
        request.setUseVerYn('Y');

        result = dao.firstSave(request);
        tempSave(request);

        if(result == 0){
            throw new NoUpdatedDataException();
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
        result += updateMatchedSpec(param);

        if (result != 5) {
            throw new NoCreatedDataException();
        }

    }

    private boolean isWrap(ItemManageVO param) {
        String pitmTyp = param.getPitmTyp();
        return pitmTyp.equals(PItemType.PACKAGING_MATERIAL.getCode())
                || pitmTyp.equals(PItemType.FINISHED_SET.getCode())
                || pitmTyp.equals(PItemType.FINISHED_SINGLE.getCode());
    }

    public int updateMatchedSpec(ItemManageVO param){
        ItemApprSpecVO itemSpecVO = Optional.ofNullable(dao.findOldSpecInfo(param)).orElse(new ItemApprSpecVO());
        Integer oldPItemSpecIdx = Optional.ofNullable(itemSpecVO.getPitmSpecIdx()).orElse(0);
        Integer oldAItemSpecIdx = Optional.ofNullable(itemSpecVO.getOldAitmSpecIdx()).orElse(0);
        String specProcCode = Optional.ofNullable(itemSpecVO.getSpecProcCd()).orElse("");

        itemSpecVO.setDelYn("N");
        itemSpecVO.setUseVerYn("N");
        itemSpecVO.setPlntCd(param.getPlntCd());
        itemSpecVO.setPitmCd(param.getPitmCd());
        itemSpecVO.setPitmVer(param.getPitmVer() + 1);
        itemSpecVO.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
        ItemManageVO newVO = dao.findNewAitmSpecIdx(itemSpecVO);
        itemSpecVO.setAitmSpecVer(newVO.getAitmSpecVer());

        if (oldAItemSpecIdx == 0 || specProcCode.equals(SpecProgress.TEMPORARY_STORAGE.getCode())){
            return dao.updateSpecNewPItemVer(itemSpecVO);
        }

        if (specProcCode.equals(SpecProgress.APPROVED.getCode())){
            if(!isWrap(param)){
                itemSpecVO.setAitmSpecIdx(newVO.getAitmSpecIdx());
                dao.createQmPitmAitmSpec(itemSpecVO);
                dao.createQmPitmSpecAitm(itemSpecVO);
            }else{
                itemSpecVO.setAitmSpecIdx(itemSpecVO.getOldAitmSpecIdx());
            }
            dao.updateOldSpecUseVerN(itemSpecVO);
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