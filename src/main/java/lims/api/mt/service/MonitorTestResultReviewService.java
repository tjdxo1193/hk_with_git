package lims.api.mt.service;

import lims.api.mt.vo.MonitorTestResultReviewVO;

import java.util.List;

public interface MonitorTestResultReviewService {
    List<MonitorTestResultReviewVO> getMonitorTestResultReviewList(MonitorTestResultReviewVO request);
    List<MonitorTestResultReviewVO> getMonitorTestRst(MonitorTestResultReviewVO request);

    void apprRequest(MonitorTestResultReviewVO request);
    void reject(MonitorTestResultReviewVO request);
    void hold(MonitorTestResultReviewVO request);
}
