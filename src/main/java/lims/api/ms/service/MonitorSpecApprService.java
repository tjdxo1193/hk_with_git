package lims.api.ms.service;

import lims.api.ms.vo.MonitorSpecApprVO;

import java.util.List;

public interface MonitorSpecApprService {

    List<MonitorSpecApprVO> getVersionList(MonitorSpecApprVO param);

    List<MonitorSpecApprVO> getAItemList(MonitorSpecApprVO param);

    void updateApproval(List<MonitorSpecApprVO> param);

    void updateReject(List<MonitorSpecApprVO> param);
}
