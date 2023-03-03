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

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.NotFoundException;
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
    public void createFirstVersion(SpecManageVO param) {
        log.info("[bug report] added rows count: {}, edited rows count: {}.", param.getAddedRowItems().size(), param.getEditedRowItems().size());
        int createResult = 0;
        int updateResult = 0;
        int addRowResult = 0;
        param.setAitmSpecVer(1); // 첫번째 버전이기 때문에 1 부터 시작

        createResult = dao.createAitmSpec(param);
        if (createResult == 0) {
            throw new NoCreatedDataException();
        }

        updateResult = dao.updateAitmSpecIdx(param);
        if (updateResult == 0) {
            throw new NoUpdatedDataException();
        }

        for (SpecManageAitmVO svo : param.getAddedRowItems()) {
            log.info("[bug report] create spec aitm. aitmSpecIdx: {}.", svo.getAitmSpecIdx());
            svo.setPlntCd(param.getPlntCd());
            svo.setAitmSpecIdx(param.getAitmSpecIdx());
            addRowResult += dao.createPItemSpecAItem(svo);
        }

        if (addRowResult != param.getAddedRowItems().size()) {
            throw new NoUpdatedDataException();
        }

    }

    @Override
    public void saveAItemList(SpecManageVO param) {
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
    public void createNewVersion(SpecManageVO param) {
        log.info("[bug report] added rows count: {}, edited rows count: {}.", param.getAddedRowItems().size(), param.getEditedRowItems().size());
        int createAitmSpecResult = 0;
        int createPItemSpecResult = 0;
        int updateResult = 0;

        // 시험 항목 규격을 먼저 INSERT
        param.setAitmSpecVer(param.getAitmSpecVer() + 1);
        param.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());

        createAitmSpecResult = dao.createAitmSpec(param);
        if (createAitmSpecResult == 0) {
            throw new NoCreatedDataException();
        }

        updateResult = dao.updateUseVerN(param);
        if (updateResult == 0) {
            throw new NoCreatedDataException();
        }

        createPItemSpecResult = dao.createPItemSpec(param);
        if (createPItemSpecResult == 0) {
            throw new NoCreatedDataException();
        }

        saveAItemList(param);
    }

    @Override
    public void updateNewVersion(SpecManageVO param) {
        int equalPreviousVersion = dao.checkPreviousAItemIdx(param);
        if(equalPreviousVersion == 0 ){
            throw new NotFoundException("I couldn't find the spec sheet where the previous version had the same aitmIdx value.");
        }

        log.info("[bug report] added rows count: {}, edited rows count: {}.", param.getAddedRowItems().size(), param.getEditedRowItems().size());
        int createAitmSpecResult = 0;
        int updateAitmSpecIdxResult = 0;
        int addRowResult = 0;

        // 시험 항목 규격을 먼저 INSERT
        param.setAitmSpecVer(param.getAitmSpecVer() + 1);

        createAitmSpecResult = dao.createAitmSpec(param);
        if (createAitmSpecResult == 0) {
            throw new NoCreatedDataException();
        }

        updateAitmSpecIdxResult = dao.updateAitmSpecIdx(param);
        if (updateAitmSpecIdxResult == 0) {
            throw new NoUpdatedDataException();
        }

        for (SpecManageAitmVO svo : param.getAddedRowItems()) {
            log.info("[bug report] create spec aitm. aitmSpecIdx: {}.", svo.getAitmSpecIdx());
            svo.setPlntCd(param.getPlntCd());
            svo.setAitmSpecIdx(param.getAitmSpecIdx());
            addRowResult += dao.createPItemSpecAItem(svo);
        }

        if (addRowResult != param.getAddedRowItems().size()) {
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

    @Override
    @Transactional
    public int putPkgaCd(SpecManageVO param) {
        int result = 0;

        // 품목 테이블에는 pkgaCd가 저장되어야 한다.
        SpecManageVO qmiPitmDto = new SpecManageVO();
        qmiPitmDto.setPlntCd(param.getPlntCd());
        qmiPitmDto.setPitmCd(param.getPitmCd());
        qmiPitmDto.setPitmVer(param.getPitmVer());
        qmiPitmDto.setPkgaCd(param.getPkgaCd());
        result += dao.updatePkgaCd(qmiPitmDto);

        // 규격서 테이블에는 aitmSpecIdx가 저장되어야 한다.
        SpecManageVO qmPitmSpecDto = new SpecManageVO();
        qmPitmSpecDto.setPlntCd(param.getPlntCd());
        qmPitmSpecDto.setPitmSpecIdx(param.getPitmSpecIdx());
        qmPitmSpecDto.setAitmSpecIdx(param.getAitmSpecIdx());
        result += dao.updateAitmSpecIdx(qmPitmSpecDto);

        if (result == 0) {
            throw new NoCreatedDataException();
        }

        return result;
    }
}