package lims.api.integration.service;

import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.enums.rfc.RFCParamOfAssets;
import lims.api.integration.enums.rfc.RFCParamOfAssetsDepreciation;
import lims.api.integration.enums.rfc.RFCParamOfBusinessPartner;
import lims.api.integration.vo.rfc.RFCAssetsDepreciationVO;
import lims.api.integration.vo.rfc.RFCAssetsVO;
import lims.api.integration.vo.rfc.RFCBusinessPartnerVO;
import lims.api.integration.vo.rfc.RFCCommonCodeVO;

import java.util.List;

public interface RFCService {

    List<RFCCommonCodeVO> getCommonCode(RFCParam param);

    List<RFCAssetsVO> getAssets();

    List<RFCAssetsVO> getAssets(RFCParam<RFCParamOfAssets, String> param);

    List<RFCBusinessPartnerVO> getBusinessPartner(RFCParam<RFCParamOfBusinessPartner, String> param);

}