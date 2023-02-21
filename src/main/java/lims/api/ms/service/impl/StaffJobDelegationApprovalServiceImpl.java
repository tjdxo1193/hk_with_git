package lims.api.ms.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.dao.StaffJobDelegationApprovalDao;
import lims.api.ms.enums.UserDelegate;
import lims.api.ms.enums.UserDelegateAppr;
import lims.api.ms.service.StaffJobDelegationApprovalService;
import lims.api.ms.vo.StaffJobDelegationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffJobDelegationApprovalServiceImpl implements StaffJobDelegationApprovalService {
    private final StaffJobDelegationApprovalDao dao;
    private final ApproveService approveService;

    @Override
    public List<StaffJobDelegationVO> find(StaffJobDelegationVO param) {
        return dao.find(param);
    }

    @Override
    public void updateApprove(List<StaffJobDelegationVO> params) {
        int result = 0;

        for (StaffJobDelegationVO row : params) {
            approveService.approve(row.getDlgAprReqIdx());
            row.setDlgProcCd(UserDelegateAppr.COMPLETE.getValue());
            row.setDlgSttCd(UserDelegate.DELEGATE.getValue());
            result += dao.updateApprove(row);
        }

        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void updateReject(List<StaffJobDelegationVO> params) {
        int result = 0;

        for (StaffJobDelegationVO row : params) {
            row.setDlgProcCd(UserDelegateAppr.RETURN.getValue());
            result += dao.updateReject(row);
        }

        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }
}