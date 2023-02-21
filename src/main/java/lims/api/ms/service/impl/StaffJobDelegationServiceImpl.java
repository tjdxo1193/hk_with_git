package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.dao.StaffJobDelegationDao;
import lims.api.ms.enums.UserDelegate;
import lims.api.ms.enums.UserDelegateAppr;
import lims.api.ms.service.StaffJobDelegationService;
import lims.api.ms.vo.StaffJobDelegationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffJobDelegationServiceImpl implements StaffJobDelegationService {
    private final StaffJobDelegationDao dao;
    private final ApproveService approveService;

    @Override
    public List<StaffJobDelegationVO> find(StaffJobDelegationVO param) {
        return dao.find(param);
    }

    @Override
    public void create(StaffJobDelegationVO param) {
        param.setDlgProcCd(UserDelegateAppr.TEMP_SAVE.getValue());
        int result = dao.create(param);
        if (result == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(StaffJobDelegationVO param) {
        int result = dao.update(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void approveRequest(StaffJobDelegationVO param) {
        int result = 0;
        ApproveVO approveInfo = setApproveVO(param);
        if (param.getDlgAprReqIdx() != null) {
            approveInfo.setAprIdx(param.getDlgAprReqIdx());
            approveService.update(approveInfo);
        } else {
            approveService.create(approveInfo);
        }
        param.setDlgAprReqIdx(approveInfo.getAprIdx());
        param.setDlgProcCd(UserDelegateAppr.REQUEST.getValue());
        result = dao.updateApprRequest(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void updateGbkRegist(StaffJobDelegationVO param) {
        param.setDlgSttCd(UserDelegate.STAY.getValue());
        int result = dao.updateGbkRegist(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    private ApproveVO setApproveVO(StaffJobDelegationVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqDiv(param.getAprReqDiv());
        approveInfo.setAprUid(param.getAprUid());
        return approveInfo;
    }
}
