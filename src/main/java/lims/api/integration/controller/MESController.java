package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.model.MESRequestForFinalOrder;
import lims.api.integration.model.MESRequestForPackageSpecReport;
import lims.api.integration.model.MESResponse;
import lims.api.integration.service.MESService;
import lims.api.integration.service.impl.InterfaceControllerTemplate;
import lims.api.integration.vo.MESSendVO;
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
@RequestMapping("${apiPrefix}/interface/mes")
@RequiredArgsConstructor
public class MESController {

    private final InterfaceControllerTemplate controllerTemplate;
    private final MESService mesService;

    @Permit
    @PostMapping("report/package-spec")
    public ResponseEntity<MESResponse> savePackageSpecReport(MultipartHttpServletRequest request) {
        MESRequestForPackageSpecReport param = MultipartHttpUtil.getEAIParameter(request, MESRequestForPackageSpecReport.class);
        return controllerTemplate.execute(
                RevInterface.MES_PACKAGE_SPEC_REPORT,
                param,
                infoIdx -> {
                    List<MultipartFile> files = MultipartHttpUtil.getFiles(request);
                    mesService.savePackageSpecReport(infoIdx, param.getDataList(), files);
                    return ResponseEntity.ok(MESResponse.toSuccessResponse(param));
                },
                (e, message) -> ResponseEntity.ok(MESResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("final-order")
    public ResponseEntity<MESResponse> finalOrder(@RequestBody MESRequestForFinalOrder param) {
        return controllerTemplate.execute(
                RevInterface.MES_FINAL_ORDER,
                param,
                infoIdx -> {
                    mesService.saveFinalOrder(infoIdx, param.getDataList());
                    return ResponseEntity.ok(MESResponse.toSuccessResponse(param));
                },
                (e, message) -> ResponseEntity.ok(MESResponse.toErrorResponse(param, message))
        );
    }

    @Permit
    @PostMapping("devTest/testStatus")
    public void devTestOfTestStatus(@RequestBody MESSendVO.TestStatus param) {
        mesService.publishTestStatus(param);
    }

    @Permit
    @PostMapping("devTest/testResult")
    public void devTestOfTestResult(@RequestBody MESSendVO.TestResult param) {
        mesService.publishTestResultJudgment(param);
    }

    @Permit
    @PostMapping("devTest/nonCfmReport")
    public void devTestOfNonCfmReport(@RequestBody MESSendVO.NonCfmReport param) {
        File file = new File("C:\\Users\\user\\Pictures\\" + param.getFileName());
        mesService.publishNonCfmReport(param, FileUtil.getName(file), FileUtil.toBytes(file));
    }

}