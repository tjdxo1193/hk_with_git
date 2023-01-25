package lims.api.integration.service.impl;

import lims.api.integration.domain.rfc.BusinessPartnerRFC;
import lims.api.integration.domain.rfc.CommonCodeRFC;
import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.service.RFCService;
import lims.api.integration.vo.rfc.BusinessPatnerRFCVO;
import lims.api.integration.vo.rfc.CommonCodeRFCVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RFCServiceImpl implements RFCService {

    private final RFCResolver rfcResolver;

    @Override
    public List<CommonCodeRFCVO> getCommonCode(RFCParam param) {
        return rfcResolver.getExecutor(new CommonCodeRFC()).execute(param, CommonCodeRFCVO[].class);
    }

    @Override
    public BusinessPatnerRFCVO getBusinessPartner(RFCParam param) {
        param.put("CHECK", "03");
        return rfcResolver.getExecutor(new BusinessPartnerRFC()).executeOne(param, BusinessPatnerRFCVO.class);
    }

    @Override
    public List<BusinessPatnerRFCVO> getBusinessPartners(RFCParam param) {
        param.put("CHECK", "02");
        return rfcResolver.getExecutor(new BusinessPartnerRFC()).execute(param, BusinessPatnerRFCVO[].class);
    }
}