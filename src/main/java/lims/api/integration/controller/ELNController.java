package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.common.enums.UseType;
import lims.api.integration.enums.ELNCmdType;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.model.ELNRequestForCtReport;
import lims.api.integration.model.ELNRequestForStandardSpec;
import lims.api.integration.model.ELNResponse;
import lims.api.integration.service.ELNService;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.service.impl.InterfaceControllerTemplate;
import lims.api.integration.vo.ELNCtReportVO;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${apiPrefix}/interface/eln")
@RequiredArgsConstructor
public class ELNController {

    private final InterfaceControllerTemplate controllerTemplate;
    private final ELNService elnService;
    private final IntegrationSender sender;
    private final JdbcTemplate jdbcTemplate;

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
                (e, message) -> ResponseEntity.ok(ELNResponse.toErrorResponse(e, message))
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
        List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT AMITM_CD, USE_YN FROM MS_AMITM");

        List<InterfaceSendVO.MethodByItem> param = new ArrayList<>();
        for (Map<String, Object> result : results) {
            String code = String.valueOf(result.get("AMITM_CD"));
            boolean isCreate = String.valueOf(result.get("USE_YN")).equals(UseType.Y.name());
            param.add(InterfaceSendVO.MethodByItem.builder()
                    .amitmCd(code)
                    .cmdType(isCreate ? ELNCmdType.C : ELNCmdType.D)
                    .build());
        }
        sender.sendMethodByItem(param);
    }

}