package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.enums.rfc.RFCBusinessPartnerCheck;
import lims.api.integration.enums.rfc.RFCParamOfAssets;
import lims.api.integration.enums.rfc.RFCParamOfBusinessPartner;
import lims.api.integration.service.RFCService;
import lims.api.integration.vo.rfc.RFCAssetsVO;
import lims.api.integration.vo.rfc.RFCBusinessPartnerVO;
import lims.api.schedule.service.impl.BusinessPartnerScheduler;
import lims.api.schedule.service.impl.ManufactureInputPerformanceScheduler;
import lims.api.schedule.service.impl.PurchaseInputPerformanceScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${apiPrefix}/rfc/devTest")
@RequiredArgsConstructor
public class RFCController {

    private final RFCService rfcService;

    private final PurchaseInputPerformanceScheduler purchaseInputPerformanceScheduler;
    private final ManufactureInputPerformanceScheduler manufactureInputPerformanceScheduler;

    private final BusinessPartnerScheduler scheduler;

    @Permit
    @GetMapping("scheduler/{code}")
    public void commonCode(@PathVariable String code) {
        switch(code) {
            case "ms":
                manufactureInputPerformanceScheduler.run();
                break;
            case "ps":
                purchaseInputPerformanceScheduler.run();
                break;
            default:
                break;
        }
    }

    @Permit
    @GetMapping("assets")
    public void assets() {
        RFCParam<RFCParamOfAssets, String> param = new RFCParam<>();
        List<RFCAssetsVO> results =  rfcService.getAssetsMaster(param);
        log.info("Call common code RFC. result size: {}", results.size());
        for (RFCAssetsVO vo : results) {
            log.info(vo.toString());
        }
    }

    @Permit
    @GetMapping("bp")
    public void bp() {
        RFCParam<RFCParamOfBusinessPartner, String> recentParam = new RFCParam<>();
        recentParam.put(RFCParamOfBusinessPartner.CHECK, RFCBusinessPartnerCheck.RECENT_10_DAYS.getCode());
        List<RFCBusinessPartnerVO> recent = rfcService.getBusinessPartner(recentParam);
        log.info("recent 10 days data size: {}", recent.size());

        RFCParam<RFCParamOfBusinessPartner, String> migrationParam = new RFCParam<>();
        migrationParam.put(RFCParamOfBusinessPartner.CHECK, RFCBusinessPartnerCheck.ALL_FOR_MIGRATION.getCode());
        List<RFCBusinessPartnerVO> migration = rfcService.getBusinessPartner(migrationParam);
        log.info("migration data size: {}", migration.size());
    }

    @Permit
    @GetMapping("bpWithName")
    public void bpWithName() {
        scheduler.runMigration();
    }


}