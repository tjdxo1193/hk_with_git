package lims.api.mt.service;

import lims.api.mt.vo.MonitorTestReceiptVO;

import java.util.List;

public interface MonitorTestReceiptService {
    List<MonitorTestReceiptVO> getMonitorTestReceiptList(MonitorTestReceiptVO request);
    List<MonitorTestReceiptVO> getMonitorTestMitm(MonitorTestReceiptVO request);
    void receipt(List<MonitorTestReceiptVO> request);
}
