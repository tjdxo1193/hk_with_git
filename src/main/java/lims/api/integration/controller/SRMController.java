package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.model.SRMRequestForFinalOrder;
import lims.api.integration.model.SRMRequestForReoccurPreventReport;
import lims.api.integration.model.SRMRequestForSupplierReport;
import lims.api.integration.model.SRMResponse;
import lims.api.integration.service.SRMService;
import lims.api.integration.service.impl.InterfaceControllerTemplate;
import lims.api.integration.vo.SRMSendVO;
import lims.api.util.FileUtil;
import lims.api.util.MultipartHttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${apiPrefix}/interface/srm")
@RequiredArgsConstructor
public class SRMController {

    private final InterfaceControllerTemplate controllerTemplate;
    private final SRMService srmService;

    @Permit
    @PostMapping("report/reoccur-prevention")
    public ResponseEntity<SRMResponse> saveReoccurPreventReport(MultipartHttpServletRequest request) {
        SRMRequestForReoccurPreventReport param = MultipartHttpUtil.getEAIParameter(request, SRMRequestForReoccurPreventReport.class);
        return controllerTemplate.execute(
                RevInterface.SRM_REOCCUR_PREVENT_REPORT,
                param,
                infoIdx -> {
                    List<MultipartFile> files = MultipartHttpUtil.getFiles(request);
                    srmService.saveReoccurPreventReport(infoIdx, param.getDataList(), files);
                    return ResponseEntity.ok(SRMResponse.toSuccessResponse(param));
                },
                (e, message) -> ResponseEntity.ok(SRMResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("report/consignment-and-supplier")
    public ResponseEntity<SRMResponse> saveSupplierReport(MultipartHttpServletRequest request) {
        SRMRequestForSupplierReport param = MultipartHttpUtil.getEAIParameter(request, SRMRequestForSupplierReport.class);
        return controllerTemplate.execute(
                RevInterface.SRM_CONSIGNMENT_AND_SUPPLIER_REPORT,
                param,
                infoIdx -> {
                    List<MultipartFile> files = MultipartHttpUtil.getFiles(request);
                    srmService.saveSupplierReport(infoIdx, param.getDataList(), files);
                    return ResponseEntity.ok(SRMResponse.toSuccessResponse(param));
                },
                (e, message) -> ResponseEntity.ok(SRMResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("final-order")
    public ResponseEntity<SRMResponse> finalOrder(@RequestBody SRMRequestForFinalOrder param) {
        return controllerTemplate.execute(
                RevInterface.SRM_FINAL_ORDER,
                param,
                infoIdx -> {
                    srmService.saveFinalOrder(infoIdx, param.getDataList());
                    return ResponseEntity.ok(SRMResponse.toSuccessResponse(param));
                },
                (e, message) -> ResponseEntity.ok(SRMResponse.toErrorResponse(param, message))
        );
    }


    @Permit
    @PostMapping("devTest/testStatus")
    public void devTestTestStatus(@RequestBody SRMSendVO.TestStatus param) {
        srmService.publishTestStatus(param);
    }

    @Permit
    @PostMapping("devTest/testResult")
    public void devTestOfTestResult(@RequestBody SRMSendVO.TestResult param) {
        srmService.publishTestResultJudgment(param);
    }

    @Permit
    @PostMapping("devTest/nonCfmReport")
    public void devTestOfNonCfmReport(@RequestBody SRMSendVO.NonCfmReport param) {
        File file = new File("C:\\Users\\user\\Pictures\\" + param.getFileName());
        srmService.publishNonCfmReport(param, FileUtil.getName(file), FileUtil.toBytes(file));
    }

}