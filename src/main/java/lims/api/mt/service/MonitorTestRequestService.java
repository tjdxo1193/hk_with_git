package lims.api.mt.service;

import lims.api.mt.vo.MonitorTestRequestVO;

import java.util.List;

public interface MonitorTestRequestService {
    List<MonitorTestRequestVO> getMonitorTestRequestList(MonitorTestRequestVO request);

    void request(List<MonitorTestRequestVO> request);

    void requestCancel(List<MonitorTestRequestVO> request);
}
