package lims.api.mt.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.mt.dao.MonitorTestResultCancelDao;
import lims.api.mt.enums.MonitorTestProcess;
import lims.api.mt.service.MonitorTestResultCancelService;
import lims.api.mt.vo.MonitorTestResultCancelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorTestResultCancelServiceImpl implements MonitorTestResultCancelService {

    private final MonitorTestResultCancelDao dao;

    @Override
    public List<MonitorTestResultCancelVO> getMonitorTestResultCancelList(MonitorTestResultCancelVO request) {
        return dao.getMonitorTestResultCancelList(request);
    }

    @Override
    public List<MonitorTestResultCancelVO> getMonitorTestRst(MonitorTestResultCancelVO request) {
        return dao.getMonitorTestRst(request);
    }

    @Override
    public void cancel(MonitorTestResultCancelVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_CANCEL.getProcessCode());
        int result = dao.cancel(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void holdnOff(MonitorTestResultCancelVO request) {
        int result = dao.holdnOff(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
