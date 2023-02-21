package lims.api.mt.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.mt.dao.MonitorTestReceiptDao;
import lims.api.mt.enums.MonitorTestProcess;
import lims.api.mt.service.MonitorTestReceiptService;
import lims.api.mt.vo.MonitorTestReceiptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorTestReceiptServiceImpl implements MonitorTestReceiptService {

    private final MonitorTestReceiptDao dao;
    private final ApproveService approveService;

    @Override
    public List<MonitorTestReceiptVO> getMonitorTestReceiptList(MonitorTestReceiptVO request) {
        request.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_RECEIPT.getProcessCode());
        return dao.getMonitorTestReceiptList(request);
    }

    @Override
    public List<MonitorTestReceiptVO> getMonitorTestMitm(MonitorTestReceiptVO request) {
        return dao.getMonitorTestMitm(request);
    }

    @Override
    public void receipt(List<MonitorTestReceiptVO> request) {
        int result = 0;
        for (MonitorTestReceiptVO item : request) {
            item.setAnsProcCd(MonitorTestProcess.MONITOR_TEST_INSTRUCTION.getProcessCode());
            int assAprReqIdx = approveService.requestApprove(item.getApproveInfo());
            item.setAssAprReqIdx(assAprReqIdx);
            result += dao.receipt(item);
            dao.insertRst(item);
        }

        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }
    }
}
