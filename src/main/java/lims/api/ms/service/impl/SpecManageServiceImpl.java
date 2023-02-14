package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ms.dao.SpecManageDao;
import lims.api.ms.enums.ELNProductDiv;
import lims.api.ms.enums.PItemType;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.SpecManageService;
import lims.api.ms.vo.SpecManageAitmVO;
import lims.api.ms.vo.SpecManageDptVO;
import lims.api.ms.vo.SpecManagePitmVO;
import lims.api.ms.vo.SpecManageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecManageServiceImpl implements SpecManageService {

    private final SpecManageDao dao;

    @Override
    public List<SpecManagePitmVO> getPItemList(SpecManageVO param) {
        param.setQpSpecProcCd(SpecProgress.APPROVED.getCode());
        return dao.getPItemList(param);
    }

    @Override
    public List<SpecManageVO> getVersionList(SpecManageVO param) {
        return dao.getVersionList(param);
    }

    @Override
    public List<SpecManageAitmVO> getAItemList(SpecManageVO param) {
        return dao.getAItemList(param);
    }

    @Override
    public List<SpecManageAitmVO> getSemiAItemList(SpecManageVO param) {
        if (PItemType.SEMI_MANUFACTURES_FILLING_FOAM.getCode().equals(param.getPitmTyp())
                || PItemType.SEMI_MANUFACTURES_OTHER_PRODUCT.getCode().equals(param.getPitmTyp())) {
            param.setPrdDiv(ELNProductDiv.F.name());
        } else if (PItemType.SEMI_MANUFACTURES_BASE.getCode().equals(param.getPitmTyp())
                || PItemType.SEMI_MANUFACTURES_BULK.getCode().equals(param.getPitmTyp())) {
            param.setPrdDiv(ELNProductDiv.S.name());
        }
        return dao.getSemiAItemList(param);
    }

    @Override
    public List<SpecManageVO> getItemMethodList(SpecManageVO param) {
        return dao.getItemMethodList(param);
    }

    @Override
    public List<SpecManageDptVO> getDepartmentList(SpecManageVO param) {
        return dao.getDepartmentList(param);
    }

    @Override
    public void updateVersion(SpecManageVO param) {
        // 시험 항목 규격을 먼저 INSERT
        log.info("[bug report] added rows count: {}, edited rows count: {}.", param.getAddedRowItems().size(), param.getEditedRowItems().size());
        dao.createAitmSpec(param);
        String specProgressCode = param.getSpecProcCd();

        // 규격서진행상태 , 시험 항목 규격 IDX 값을 리스트에 넣어주고
        param.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
        int result = 1;

        // 규격서 이전버전 사용버전 N으로 업데이트 및 만들어진 규격서에 임시저장이면 업데이트, 수정(개정)이면 insert
        if (specProgressCode.equals(SpecProgress.APPROVED.getCode())) {
            log.info("[bug report] approved");
            result *= dao.updateUseVerN(param);
            result *= dao.createPItemSpec(param);
        } else if (specProgressCode.equals(SpecProgress.TEMPORARY_STORAGE.getCode())) {
            log.info("[bug report] temporary storage");
            result = dao.updatePItemSpec(param);
        }

        if (result == 0) {
            throw new NoCreatedDataException();
        }

        makeAItem(param);
    }

    @Override
    public void makeAItem(SpecManageVO param) {
        log.info("[bug report] added rows count: {}, edited rows count: {}.", param.getAddedRowItems().size(), param.getEditedRowItems().size());
        int isQueryCompleted = 0;
        for (SpecManageAitmVO svo : param.getRemovedRowItems()) {
            svo.setPlntCd(param.getPlntCd());
            svo.setAitmSpecIdx(param.getAitmSpecIdx());
            isQueryCompleted += dao.deletePItemSpecAItem(svo);
        }

        for (SpecManageAitmVO svo : param.getAddedRowItems()) {
            log.info("[bug report] create spec aitm. aitmSpecIdx: {}.", svo.getAitmSpecIdx());
            svo.setPlntCd(param.getPlntCd());
            svo.setAitmSpecIdx(param.getAitmSpecIdx());
            isQueryCompleted += dao.createPItemSpecAItem(svo);
        }

        for (SpecManageAitmVO svo : param.getEditedRowItems()) {
            svo.setPlntCd(param.getPlntCd());
            svo.setAitmSpecIdx(param.getAitmSpecIdx());
            isQueryCompleted += dao.updatePItemSpecAItem(svo);
        }

        if (isQueryCompleted != param.getRemovedRowItems().size()
                + param.getAddedRowItems().size()
                + param.getEditedRowItems().size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void updateRequestReview(SpecManageVO param) {
        param.setSpecProcCd(SpecProgress.REQUEST_REVIEW.getCode());
        int result = dao.updateRequestReview(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public List<SpecManagePitmVO> getPItemSpecListToModal(SpecManageVO param) {
        param.setSpecProcCd(SpecProgress.APPROVED.getCode());
        param.setQpSpecProcCd(SpecProgress.APPROVED.getCode());
        return dao.getPItemSpecListToModal(param);
    }

    @Override
    public List<SpecManageAitmVO> getAItemListToModal(SpecManageVO param) {
        return dao.getAItemListToModal(param);
    }

    @Override
    public List<SpecManagePitmVO> getPackagingItemListToModal(SpecManageVO param) {
        param.setQpSpecProcCd(SpecProgress.APPROVED.getCode());
        return dao.getPackagingItemListToModal(param);
    }

    @Override
    public List<SpecManageVO> getSemiPItemListToModal(SpecManageVO param) {
        return dao.getSemiPItemListToModal(param);
    }

    @Override
    public List<SpecManageAitmVO> getSemiAItemListToModal(SpecManageVO param) {
        return dao.getSemiAItemListToModal(param);
    }
}