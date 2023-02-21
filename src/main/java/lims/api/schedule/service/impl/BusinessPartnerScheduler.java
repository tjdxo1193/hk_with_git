package lims.api.schedule.service.impl;

import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.enums.CompanyType;
import lims.api.integration.enums.RfcInterface;
import lims.api.integration.enums.rfc.RFCBusinessPartnerCheck;
import lims.api.integration.enums.rfc.RFCParamOfBusinessPartner;
import lims.api.integration.service.RFCService;
import lims.api.integration.service.SAPService;
import lims.api.integration.service.impl.RfcTemplate;
import lims.api.integration.vo.SAPBusinessPartnerVO;
import lims.api.integration.vo.rfc.RFCBusinessPartnerVO;
import lims.api.schedule.service.Scheduler;
import lims.api.util.process.KeyObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessPartnerScheduler implements Scheduler {

    private final RFCService rfcService;

    private final SAPService sapService;

    private final RfcTemplate rfcTemplate;

//    @Scheduled(cron = "0 0 2 * * *")
    @Override
    public void run() {
        save(RFCBusinessPartnerCheck.RECENT_10_DAYS);
    }

    /**
     * 마이그레이션 용도로 사용합니다.
     */
    public void runMigration() {
        save(RFCBusinessPartnerCheck.ALL_FOR_MIGRATION);
    }

    private void save(RFCBusinessPartnerCheck check) {
        rfcTemplate.execute(RfcInterface.BUSINESS_PARTNER, infoIdx -> {
            RFCParam<RFCParamOfBusinessPartner, String> param = new RFCParam<>();
            param.put(RFCParamOfBusinessPartner.CHECK, check.getCode());

            List<SAPBusinessPartnerVO> partners = getBusinessPartners(param);

            sapService.saveBusinessPartner(infoIdx, partners);
        });
    }

    private List<SAPBusinessPartnerVO> getBusinessPartners(RFCParam<RFCParamOfBusinessPartner, String> param) {
        List<RFCBusinessPartnerVO> list = rfcService.getBusinessPartner(param);
        Map<KeyObject, SAPBusinessPartnerVO> addedData = new HashMap<>();
        return list.stream()
                .map(vo -> {
                    SAPBusinessPartnerVO el = SAPBusinessPartnerVO.builder()
                            .companyCode(StringUtils.isEmpty(vo.getBukrs()) ? CompanyType.KOLMAR.getCode() : vo.getBukrs())
                            .partnerNo(vo.getPartner())
                            .partnerGroup(vo.getBuGroup())
                            .nameOrg1(vo.getNameOrg1())
                            .nameOrg2(vo.getNameOrg2())
                            .companyNumber(vo.getTaxnum2())
                            .build();
                    /**
                     * 테스트 데이터 중 중복 키의 값이 있어 필터 처리함.
                     * 운영 환경에서는 중복 키가 없을 것으로 보이므로 데이터가 누락되는 경우는 없을 것이라 생각됨.
                     */
                    KeyObject key = el.generateKey();
                    if (!addedData.containsKey(key)) {
                        addedData.put(key, el);
                    }
                    return el;
                })
                .filter(vo -> vo == addedData.get(vo.generateKey()))
                .collect(Collectors.toList());
    }
}