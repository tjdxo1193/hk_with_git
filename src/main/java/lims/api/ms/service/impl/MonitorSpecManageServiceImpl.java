package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.vo.ComboVO;
import lims.api.ms.dao.MonitorSpecManageDao;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.MonitorSpecManageService;
import lims.api.ms.vo.MonitorSpecManageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorSpecManageServiceImpl implements MonitorSpecManageService {

    private final MonitorSpecManageDao dao;
    @Override
    public List<MonitorSpecManageVO> getMItemSpecList(MonitorSpecManageVO param) {
        return dao.getMItemSpecList(param);
    }

    @Override
    public List<MonitorSpecManageVO> getVersionList(MonitorSpecManageVO param) {
        return dao.getVersionList(param);
    }

    @Override
    public List<MonitorSpecManageVO> getMItemSpecAItemList(MonitorSpecManageVO param) {
        return dao.getMItemSpecAItemList(param);
    }

    @Override
    public void createNewVersion(MonitorSpecManageVO param) {
        int result = 1;
        // 전 버전 USE_VER_YN -> N
        dao.updateUseVerN(param);

        // 버전 UP (+ 1)
        param.setAitmSpecVer(param.getAitmSpecVer() + 1);
        param.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());

        result *= dao.createVersion(param);
        result *= dao.createMItemSpec(param);

        if (result == 0) {
            throw new NoCreatedDataException();
        }

        makeAItem(param);
    }

    @Override
    public void createFirstVersion(MonitorSpecManageVO param) {
        int result = 1;

        // 첫 버전 (1)
        param.setAitmSpecVer(1);
        param.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());

        result *= dao.createVersion(param);
        result *= dao.createMItemSpec(param);

        if (result == 0) {
            throw new NoCreatedDataException();
        }

        makeAItem(param);
    }

    @Override
    public void makeAItem(MonitorSpecManageVO param) {
        log.info("[bug report] added rows count: {}, edited rows count: {}.", param.getAddedRowItems().size(), param.getEditedRowItems().size());
        int isQueryCompleted = 0;

        for (MonitorSpecManageVO svo : param.getRemovedRowItems()) {
            svo.setPlntCd(param.getPlntCd());
            svo.setAitmSpecIdx(param.getAitmSpecIdx());
            isQueryCompleted += dao.deleteMItemSpecAItem(svo);
        }

        for (MonitorSpecManageVO svo : param.getAddedRowItems()) {
            log.info("[bug report] create spec aitm. aitmSpecIdx: {}.", svo.getAitmSpecIdx());
            svo.setPlntCd(param.getPlntCd());
            svo.setAitmSpecIdx(param.getAitmSpecIdx());
            isQueryCompleted += dao.createMItemSpecAItem(svo);
        }

        for (MonitorSpecManageVO svo : param.getEditedRowItems()) {
            svo.setPlntCd(param.getPlntCd());
            svo.setAitmSpecIdx(param.getAitmSpecIdx());
            isQueryCompleted += dao.updateMItemSpecAItem(svo);
        }

        if (isQueryCompleted != param.getRemovedRowItems().size()
                + param.getAddedRowItems().size()
                + param.getEditedRowItems().size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void updateRequestReview(MonitorSpecManageVO param) {
        param.setSpecProcCd(SpecProgress.REQUEST_REVIEW.getCode());
        int result = dao.updateRequestReview(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public List<MonitorSpecManageVO> getItemMethodList(MonitorSpecManageVO param) {
        return dao.getItemMethodList(param);
    }

    @Override
    public List<ComboVO> getDepartmentList(MonitorSpecManageVO param) {
        return dao.getDepartmentList(param);
    }
}
