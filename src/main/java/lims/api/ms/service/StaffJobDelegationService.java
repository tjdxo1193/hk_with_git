package lims.api.ms.service;

import lims.api.ms.vo.StaffJobDelegationVO;

import java.util.List;

public interface StaffJobDelegationService {
    List<StaffJobDelegationVO> find(StaffJobDelegationVO param);
    void create(StaffJobDelegationVO param);
    void update(StaffJobDelegationVO param);
    void approveRequest(StaffJobDelegationVO param);
    void updateGbkRegist(StaffJobDelegationVO param);
}
