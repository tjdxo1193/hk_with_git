package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.service.RFCService;
import lims.api.integration.vo.rfc.RFCAssetsDepreciationVO;
import lims.api.integration.vo.rfc.RFCAssetsVO;
import lims.api.integration.vo.rfc.RFCCommonCodeVO;
import lims.api.schedule.service.impl.MonitorTestScheduler;
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

    private final MonitorTestScheduler scheduler;

    @Permit
    @GetMapping("common-code")
    public void commonCode() {
        RFCParam param = new RFCParam();
        param.put("LGORT", "1100");
        List<RFCCommonCodeVO> results =  rfcService.getCommonCode(param);
        log.info("Call common code RFC. result size: {}", results.size());
        for (RFCCommonCodeVO vo : results) {
            log.info(vo.toString());
        }
    }

    @Permit
    @GetMapping("assets")
    public void assets() {
        scheduler.run();
//        RFCParam param = new RFCParam();
//        List<RFCAssetsVO> results =  rfcService.getAssets(param);
//        log.info("Call common code RFC. result size: {}", results.size());
//        for (RFCAssetsVO vo : results) {
//            log.info(vo.toString());
//        }
    }

    @Permit
    @GetMapping("assets-depreciation")
    public void assetsDepreciation() {
        RFCParam param = new RFCParam();
        List<RFCAssetsDepreciationVO> results =  rfcService.getAssetsDepreciation(param);
        log.info("Call common code RFC. result size: {}", results.size());
        for (RFCAssetsDepreciationVO vo : results) {
            log.info(vo.toString());
        }
    }

}