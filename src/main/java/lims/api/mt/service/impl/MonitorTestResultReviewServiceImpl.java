package lims.api.mt.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.UserService;
import lims.api.mt.dao.MonitorTestResultReviewDao;
import lims.api.mt.enums.MonitorTestProcess;
import lims.api.mt.service.MonitorTestResultReviewService;
import lims.api.mt.vo.MonitorTestResultReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorTestResultReviewServiceImpl implements MonitorTestResultReviewService {

    private final MonitorTestResultReviewDao dao;
    private final ApproveService approveService;
    private final UserService userService;

    @Override
    public List<MonitorTestResultReviewVO> getMonitorTestResultReviewList(MonitorTestResultReviewVO request) {
        List<String> processList = new ArrayList<>();
        processList.add(0, MonitorTestProcess.MONITOR_TEST_REVIEW.getProcessCode());
        processList.add(1, MonitorTestProcess.MONITOR_TEST_APPROVAL_REJECT.getProcessCode());
        request.setTestProcessList(processList);
        request.setWithDelegateUserIds(userService.getDelegateAssignUserIdsWithMe(request.getRevwUid()));
        return dao.getMonitorTestResultReviewList(request);
    }

    @Override
    public List<MonitorTestResultReviewVO> getMonitorTestRst(MonitorTestResultReviewVO request) {
        return dao.getMonitorTestRst(request);
    }

    @Override
    public void apprRequest(MonitorTestResultReviewVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_APPROVED.getProcessCode());
        int rstAprReqIdx = approveService.requestApprove(request.getApproveInfo());
        request.setRstAprReqIdx(rstAprReqIdx);
        int result = dao.apprRequest(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(MonitorTestResultReviewVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_REVIEW_REJECT.getProcessCode());
        int result = dao.reject(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void hold(MonitorTestResultReviewVO request) {
        int result = dao.hold(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
