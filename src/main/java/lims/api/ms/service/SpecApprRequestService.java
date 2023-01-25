package lims.api.ms.service;

import lims.api.ms.vo.SpecApprRequestVO;

import java.util.List;

public interface SpecApprRequestService {

    List<SpecApprRequestVO> getVersionList(SpecApprRequestVO param);
    List<SpecApprRequestVO> getAItemList(SpecApprRequestVO param);

    void updateReviewReturn(SpecApprRequestVO param);

    void updateApprovalRequest(SpecApprRequestVO param);
}
