package lims.api.mt.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.mt.dao.MonitorTestResultApprDao;
import lims.api.mt.enums.MonitorTestProcess;
import lims.api.mt.service.MonitorTestResultApprService;
import lims.api.mt.vo.MonitorTestResultApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorTestResultApprServiceImpl implements MonitorTestResultApprService {

    private final MonitorTestResultApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<MonitorTestResultApprVO> getMonitorTestResultApprList(MonitorTestResultApprVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_APPROVED.getProcessCode());
        return dao.getMonitorTestResultApprList(request);
    }

    @Override
    public List<MonitorTestResultApprVO> getMonitorTestRst(MonitorTestResultApprVO request) {
        return dao.getMonitorTestRst(request);
    }

    @Override
    public void approve(MonitorTestResultApprVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_FINISH.getProcessCode());
        int result = dao.approve(request);
        approveService.approve(request.getRstAprReqIdx());
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(MonitorTestResultApprVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_APPROVAL_REJECT.getProcessCode());
        int result = dao.reject(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void hold(MonitorTestResultApprVO request) {
        int result = dao.hold(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
