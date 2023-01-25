package lims.api.integration.service;

import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.vo.rfc.BusinessPatnerRFCVO;
import lims.api.integration.vo.rfc.CommonCodeRFCVO;

import java.util.List;

public interface RFCService {

    List<CommonCodeRFCVO> getCommonCode(RFCParam param);

    BusinessPatnerRFCVO getBusinessPartner(RFCParam param);

    List<BusinessPatnerRFCVO> getBusinessPartners(RFCParam param);

}