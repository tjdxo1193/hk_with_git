package lims.api.ms.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.ms.dao.MonitorSpecApprDao;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.MonitorSpecApprService;
import lims.api.ms.vo.MonitorSpecApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorSpecApprServiceImpl implements MonitorSpecApprService {

    private final MonitorSpecApprDao dao;

    private final ApproveService approveService;

    @Override
    public List<MonitorSpecApprVO> getVersionList(MonitorSpecApprVO param) {
        param.setSpecProcCd(SpecProgress.APPROVAL_REQUEST.getCode());
        return dao.getVersionList(param);
    }

    @Override
    public List<MonitorSpecApprVO> getAItemList(MonitorSpecApprVO param) {
        return dao.getAItmList(param);
    }

    @Override
    public void updateApproval(List<MonitorSpecApprVO> param) {
        int result = 0;
        for(MonitorSpecApprVO mvo : param){
            approveService.approve(mvo.getMitmSpecAprIdx());

            mvo.setSpecProcCd(SpecProgress.APPROVED.getCode());
            result = dao.updateApproval(mvo);
            if (result == 0) {
                throw new NoUpdatedDataException();
            }
        }
    }

    @Override
    public void updateReject(List<MonitorSpecApprVO> param) {
        int result = 0;
        for(MonitorSpecApprVO mvo : param){
            mvo.setSpecProcCd(SpecProgress.APPROVAL_REJECTION.getCode());
            result = dao.updateReject(mvo);
            if (result == 0) {
                throw new NoUpdatedDataException();
            }
        }
    }
}
