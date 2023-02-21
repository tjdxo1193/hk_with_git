package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.dao.SAPPostProcessDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.model.*;
import lims.api.integration.service.SAPService;
import lims.api.integration.service.impl.InterfaceControllerTemplate;
import lims.api.integration.service.impl.postProcessor.InterfacePostProcessorMap;
import lims.api.integration.service.impl.postProcessor.sap.SAPInputPerformancePostProcessor;
import lims.api.integration.service.impl.postProcessor.sap.SAPMaterialPostProcessor;
import lims.api.integration.service.impl.postProcessor.sap.SAPTestRequestPostProcessor;
import lims.api.integration.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/interface/sap")
public class SAPController {

    private final InterfaceControllerTemplate controllerTemplate;
    private final SAPService sapService;
    private final SAPPostProcessDao postProcessDao;

    private final InterfacePostProcessorMap postProcessorMap;

    @Permit
    @PostMapping("bom")
    public ResponseEntity<SAPResponse> saveBOM(@RequestBody SAPRequestForBOM param) {
        return controllerTemplate.execute(
                RevInterface.SAP_BOM,
                param,
                infoIdx -> {
                    sapService.saveBOM(infoIdx, param.getDataList(), param.getGuid());
                    return ResponseEntity.ok(SAPResponse.builder().guid(param.getGuid()).build());
                },
                (e, message) -> ResponseEntity.ok(SAPResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("material")
    public ResponseEntity<SAPResponse> saveMaterial(@RequestBody SAPRequestForMaterial param) {
        return controllerTemplate.execute(
                RevInterface.SAP_MATERIAL,
                param,
                infoIdx -> {
                    sapService.saveMaterial(infoIdx, SAPMaterialVO.of(param));
                    return ResponseEntity.ok(SAPResponse.toEmptyResponse());
                },
                (e, message) -> ResponseEntity.ok(SAPResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("characteristic")
    public ResponseEntity<SAPResponse> saveCharacteristic(@RequestBody SAPRequestForCharacteristic param) {
        return controllerTemplate.execute(
                RevInterface.SAP_CHARACTERISTIC,
                param,
                infoIdx -> {
                    sapService.saveCharacteristic(infoIdx, SAPCharacteristicVO.of(param));
                    return ResponseEntity.ok(SAPResponse.toEmptyResponse());
                },
                (e, message) -> ResponseEntity.ok(SAPResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("calendar")
    public ResponseEntity<SAPResponse> saveCalendar(@RequestBody SAPRequestForCalendar param) {
        return controllerTemplate.execute(
                RevInterface.SAP_CALENDAR,
                param,
                infoIdx -> {
                    sapService.saveCalendar(infoIdx, param.getDataList());
                    return ResponseEntity.ok(SAPResponse.toSuccessResponse(param));
                },
                (e, message) -> ResponseEntity.ok(SAPResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("test-request")
    public ResponseEntity<SAPResponse> testRequest(@RequestBody SAPRequestForTestRequest param) {
        return controllerTemplate.execute(
                RevInterface.SAP_TEST_REQUEST,
                param,
                infoIdx -> {
                    sapService.testRequest(infoIdx, SAPTestRequestVO.of(param));
                    return ResponseEntity.ok(SAPResponse.toSuccessResponse(param));
                },
                (e, message) -> ResponseEntity.ok(SAPResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("test-request/input-performance")
    public ResponseEntity<SAPResponse> testRequestPerformance(@RequestBody SAPRequestForInputPerformanceByBatch param) {
        return controllerTemplate.execute(
                RevInterface.SAP_INPUT_PERFORMANCE,
                param,
                infoIdx -> {
                    sapService.saveInputPerformanceByBatch(infoIdx, SAPInputPerformanceByBatchVO.of(param));
                    return ResponseEntity.ok(SAPResponse.toSuccessResponse(param));
                },
                (e, message) -> ResponseEntity.ok(SAPResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("devTest/bom")
    public void devTestBOM(@RequestBody RevStateful rev) {
        postProcessorMap.get(RevInterface.SAP_BOM).execute(rev);
    }

    @Permit
    @PostMapping("devTest/material")
    public void devTestMaterial(@RequestBody Map<String, Integer> param) {
        int begin = param.get("begin");
        int end = param.get("end");
        for (int i=begin; i <= end; i++) {
            postProcessorMap.get(RevInterface.SAP_MATERIAL).execute(new RevStateful(i, null));
        }
    }

    @Permit
    @PostMapping("devTest/calendar")
    public void devTestCalendar(@RequestBody RevStateful rev) {
        postProcessorMap.get(RevInterface.SAP_CALENDAR).execute(rev);
    }

    @Permit
    @PostMapping("devTest/testPerformance")
    public void purchaseInboundPerformance(@RequestBody SAPSendVO.TestPerformanceOfPurchaseInbound param) {
        sapService.publishTestPerformanceOfPurchaseInbound(param);
    }

    @Permit
    @PostMapping("devTest/testPerformanceManufactureInbound")
    public void manufactureInboundPerformance(@RequestBody SAPSendVO.TestPerformanceOfManufactureInbound param) {
        sapService.publishTestPerformanceOfManufactureInbound(param);
    }

    @Permit
    @PostMapping("devTest/assetsMovementHistory")
    public void assetsMovementHistory(@RequestBody List<SAPSendVO.AssetsMovementHistory> param) {
        sapService.publishAssetsMovementHistory(param);
    }

    @Permit
    @PostMapping("devTest/testStatus")
    public void testStatus(@RequestBody SAPSendVO.TestStatus param) {
        sapService.publishTestStatus(param);
    }

    @Permit
    @PostMapping("devTest/testResult")
    public void devTestOfTestResult(@RequestBody SAPSendVO.TestResult param) {
        sapService.publishTestResultJudgment(param);
    }

    /**
     * Internal LIMS.
     * 의뢰 연계 데이터 수신 후 post process 테스트
     */
    @Permit
    @GetMapping("devTest/pitmReq")
    public void pitmReqTest() {
        List<SAPTestRequestVO.RequestHeader> data = postProcessDao.findAllForReqTest();
        for (SAPTestRequestVO.RequestHeader vo : data) {
            postProcessorMap.get(RevInterface.SAP_TEST_REQUEST).execute(vo);
        }
    }

    @Permit
    @PostMapping("devTest/inputPerform")
    public void inputPerformTest(@RequestBody RevStateful rev) {
        postProcessorMap.get(RevInterface.SAP_INPUT_PERFORMANCE).execute(rev);
    }

    @Permit
    @PostMapping("devTest/pp/testRequest")
    public void devTestOfTestRequestPP(@RequestBody RevStateful rev) {
        postProcessorMap.get(RevInterface.SAP_TEST_REQUEST).execute(rev);
    }

}