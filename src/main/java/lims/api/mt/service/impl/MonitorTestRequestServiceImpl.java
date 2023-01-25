package lims.api.mt.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.mt.dao.MonitorTestRequestDao;
import lims.api.mt.enums.MonitorTestProcess;
import lims.api.mt.service.MonitorTestRequestService;
import lims.api.mt.vo.MonitorTestRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorTestRequestServiceImpl implements MonitorTestRequestService {

    private final MonitorTestRequestDao dao;

    @Override
    public List<MonitorTestRequestVO> getMonitorTestRequestList(MonitorTestRequestVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_REQUEST.getProcessCode());
        return dao.getMonitorTestRequestList(request);
    }

    @Override
    public void request(List<MonitorTestRequestVO> request) {
        int result = 0;

        for (MonitorTestRequestVO item : request) {
            item.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_RECEIPT.getProcessCode());
            result += dao.request(item);
        }

        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestCancel(List<MonitorTestRequestVO> request) {
        int result = 0;

        for (MonitorTestRequestVO item : request) {
            result += dao.requestCancel(item);
        }

        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }
    }
}
