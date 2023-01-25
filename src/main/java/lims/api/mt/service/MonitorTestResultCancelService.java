package lims.api.mt.service;

import lims.api.mt.vo.MonitorTestResultCancelVO;

import java.util.List;

public interface MonitorTestResultCancelService {
    List<MonitorTestResultCancelVO> getMonitorTestResultCancelList(MonitorTestResultCancelVO request);
    List<MonitorTestResultCancelVO> getMonitorTestRst(MonitorTestResultCancelVO request);

    void cancel(MonitorTestResultCancelVO request);
    void holdnOff(MonitorTestResultCancelVO request);
}
