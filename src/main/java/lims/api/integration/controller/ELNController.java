package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.enums.ELNCmdType;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.model.ELNRequestForCtReport;
import lims.api.integration.model.ELNRequestForStandardSpec;
import lims.api.integration.model.ELNResponse;
import lims.api.integration.service.ELNService;
import lims.api.integration.service.InterfaceInfoService;
import lims.api.integration.service.impl.InterfaceControllerTemplate;
import lims.api.integration.vo.ELNCtReportVO;
import lims.api.integration.vo.ELNSendVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/interface/eln")
@RequiredArgsConstructor
public class ELNController {

    private final InterfaceControllerTemplate controllerTemplate;
    private final ELNService elnService;

    @Permit
    @PostMapping("report/ct")
    public ResponseEntity<ELNResponse> saveCtReport(@RequestBody ELNRequestForCtReport param) {
        return controllerTemplate.execute(
                RevInterface.ELN_CT_REPORT,
                param,
                infoIdx -> {
                    elnService.saveCtReport(infoIdx, ELNCtReportVO.of(param));
                    return ResponseEntity.ok(ELNResponse.toSuccessResponse());
                },
                (e, message) ->  ResponseEntity.ok(ELNResponse.toErrorResponse(e, message))
        );
    }

    @Permit
    @PostMapping("standard/finished-and-semi")
    public ResponseEntity<ELNResponse> saveFinishedAndSemiStandard(@RequestBody ELNRequestForStandardSpec param) {
        return controllerTemplate.execute(
                RevInterface.ELN_STANDARD_FINISH_AND_SEMI,
                param,
                infoIdx -> {
                    elnService.saveFinishedAndSemiStandard(infoIdx, param.getStandard());
                    return ResponseEntity.ok(ELNResponse.toSuccessResponse());
                },
                (e, message) -> ResponseEntity.ok(ELNResponse.toErrorResponse(e, message))
        );
    }

    @Permit
    @GetMapping("devTest/testMethodByItem")
    public void test() {
        ELNSendVO.TestMethodByItem vo = new ELNSendVO.TestMethodByItem();
        vo.setAmitmCd("TM015805");
        ELNSendVO.TestMethodByItem vo1 = new ELNSendVO.TestMethodByItem();
        vo1.setAmitmCd("TM026701");
        ELNSendVO.TestMethodByItem vo2 = new ELNSendVO.TestMethodByItem();
        vo2.setAmitmCd("TM020402");

        elnService.publishTestMethodByItem(ELNCmdType.C, List.of(vo, vo1, vo2));
    }

}