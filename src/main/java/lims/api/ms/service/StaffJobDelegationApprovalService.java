package lims.api.ms.service;

import lims.api.ms.vo.StaffJobDelegationVO;

import java.util.List;

public interface StaffJobDelegationApprovalService {
    List<StaffJobDelegationVO> find(StaffJobDelegationVO param);
    void updateApprove(List<StaffJobDelegationVO> params);
    void updateReject(List<StaffJobDelegationVO> params);
}
