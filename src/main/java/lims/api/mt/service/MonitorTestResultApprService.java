package lims.api.mt.service;

import lims.api.mt.vo.MonitorTestResultApprVO;

import java.util.List;

public interface MonitorTestResultApprService {
    List<MonitorTestResultApprVO> getMonitorTestResultApprList(MonitorTestResultApprVO request);
    List<MonitorTestResultApprVO> getMonitorTestRst(MonitorTestResultApprVO request);

    void approve(MonitorTestResultApprVO request);
    void reject(MonitorTestResultApprVO request);
    void hold(MonitorTestResultApprVO request);
}
