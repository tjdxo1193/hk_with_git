package lims.api.integration.service;

import lims.api.integration.domain.eai.TrsResult;
import lims.api.integration.vo.*;

import java.util.List;

public interface SAPService {

    void saveBOM(Integer infoIdx, List<SAPBomVO> data, String rootGuid);

    void saveMaterial(Integer infoIdx, SAPMaterialVO data);

    void saveCharacteristic(Integer infoIdx, SAPCharacteristicVO data);

    void saveCalendar(Integer infoIdx, List<SAPCalendarVO> data);

    void testRequest(Integer infoIdx, SAPTestRequestVO data);

    void saveInputPerformanceByBatch(Integer infoIdx, SAPInputPerformanceByBatchVO data);

    void saveBusinessPartner(Integer infoIdx, List<SAPBusinessPartnerVO> data);

    void publishTestResultJudgment(SAPSendVO.TestResult data);

    void publishTestStatus(SAPSendVO.TestStatus data);

    TrsResult publishTestPerformanceOfPurchaseInbound(SAPSendVO.TestPerformanceOfPurchaseInbound data);

    TrsResult publishTestPerformanceOfManufactureInbound(SAPSendVO.TestPerformanceOfManufactureInbound data);

    void publishAssetsMovementHistory(List<SAPSendVO.AssetsMovementHistory> data);

}