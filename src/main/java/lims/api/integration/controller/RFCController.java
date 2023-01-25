package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.service.RFCService;
import lims.api.integration.vo.rfc.BusinessPatnerRFCVO;
import lims.api.integration.vo.rfc.CommonCodeRFCVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${apiPrefix}/rfc/devTest")
@RequiredArgsConstructor
public class RFCController {

    private final RFCService rfcService;

    @Permit
    @GetMapping("common-code")
    public void commonCode() {
        RFCParam param = new RFCParam();
        param.put("LGORT", "1100");
        List<CommonCodeRFCVO> results =  rfcService.getCommonCode(param);
        log.info("Call common code RFC. result size: {}", results.size());
        for (CommonCodeRFCVO vo : results) {
            log.info(vo.toString());
        }
    }

    @Permit
    @GetMapping("bp")
    public void bp() {
        List<BusinessPatnerRFCVO> results = rfcService.getBusinessPartners(new RFCParam());
        log.info("Call business partner RFC. result size: {}", results.size());
        for (BusinessPatnerRFCVO vo : results) {
            log.info(vo.toString());
        }
    }

}