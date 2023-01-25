package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.vo.ComboVO;
import lims.api.ms.dao.MonitorSpecManageDao;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.MonitorSpecManageService;
import lims.api.ms.vo.MonitorSpecManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public void createVersion(List<MonitorSpecManageVO> param) {
        int result = 1;
        MonitorSpecManageVO msmvo = dao.getMitmSpecIdxAndAitmSpecIdx(param.get(0).getPlntCd());

        param.forEach(element -> {
            element.setMitmSpecIdx(msmvo.getMitmSpecIdx());
            element.setAitmSpecIdx(msmvo.getAitmSpecIdx());
            element.setAitmSpecVer(element.getAitmSpecVer() == null ? 1 : element.getAitmSpecVer()+1);
            element.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
        });

        dao.updateUseVerN(param.get(0));
        result *= dao.createVersion(param.get(0));
        result *= dao.createMItemSpec(param.get(0));
        if (result == 0) {
            throw new NoCreatedDataException();
        }
        createMItemSpecAItem(param);
    }

    @Override
    public void createMItemSpecAItem(List<MonitorSpecManageVO> param) {
        dao.deleteMItemSpecAItem(param.get(0));

        int isCreateCompleted = 0;
        for(MonitorSpecManageVO tvo : param){
            isCreateCompleted = dao.createMItemSpecAItem(tvo);

            if (isCreateCompleted == 0) {
                throw new NoCreatedDataException();
            }
        }
    }

    @Override
    public void deleteMItemSpec(MonitorSpecManageVO param) {
        int result = dao.deleteMItemSpec(param);

        if (result == 0) {
            throw new NoDeletedDataException();
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
