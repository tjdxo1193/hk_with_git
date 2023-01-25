package lims.api.integration.service;

import lims.api.integration.vo.*;

import java.util.List;

public interface SAPService {

    void saveBOM(Integer infoIdx, List<SAPBomVO> data, String rootGuid);

    void saveMaterial(Integer infoIdx, SAPMaterialVO data);

    void saveCharacteristic(Integer infoIdx, SAPCharacteristicVO data);

    void saveCalendar(Integer infoIdx, List<SAPCalendarVO> data);

    void testRequest(Integer infoIdx, SAPTestRequestVO data);

    void saveInputPerformanceByBatch(Integer infoIdx, SAPInputPerformanceByBatchVO data);

    void saveTestInboundCancelNoti(SAPTestInboundCancelNotiVO data);

    void saveInstrument(SAPInstVO data);

    void publishTestResultJudgment(SAPSendVO.TestResult data);

    void publishTestStatus(SAPSendVO.TestStatus data);

    void publishTestPerformanceOfPurchaseInbound(List<SAPSendVO.TestPerformanceOfPurchaseInbound> data);

    void publishTestPerformanceOfManufactureInbound(List<SAPSendVO.TestPerformanceOfManufactureInbound> data);

    void publishAssetsMovementHistory(List<SAPSendVO.AssetsMovementHistory> data);

}