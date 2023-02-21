package lims.api.ms.service;

import lims.api.ms.vo.MonitorSpecApprRequestVO;

import java.util.List;

public interface MonitorSpecApprRequestService {
    List<MonitorSpecApprRequestVO> getVersionList(MonitorSpecApprRequestVO param);

    List<MonitorSpecApprRequestVO> getAItemList(MonitorSpecApprRequestVO param);

    void updateApprovalRequest(MonitorSpecApprRequestVO param);

    void updateReviewReturn(MonitorSpecApprRequestVO param);
}
