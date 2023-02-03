package lims.api.mt.service;

import lims.api.mt.vo.MonitorTestResultInputVO;

import java.util.List;

public interface MonitorTestResultInputService {
    List<MonitorTestResultInputVO> getMonitorTestResultInputList(MonitorTestResultInputVO request);
    List<MonitorTestResultInputVO> getMonitorTestRst(MonitorTestResultInputVO request);
    void updateRst(MonitorTestResultInputVO request);
    void requestReview(MonitorTestResultInputVO request);
    void hold(MonitorTestResultInputVO request);
}
