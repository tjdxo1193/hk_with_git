package lims.api.integration.service.impl;

import lims.api.integration.domain.rfc.*;
import lims.api.integration.enums.rfc.RFCParamOfAssets;
import lims.api.integration.enums.rfc.RFCParamOfAssetsDepreciation;
import lims.api.integration.enums.rfc.RFCParamOfBusinessPartner;
import lims.api.integration.service.RFCService;
import lims.api.integration.vo.rfc.RFCAssetsDepreciationVO;
import lims.api.integration.vo.rfc.RFCAssetsVO;
import lims.api.integration.vo.rfc.RFCBusinessPartnerVO;
import lims.api.integration.vo.rfc.RFCCommonCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RFCServiceImpl implements RFCService {

    private final RFCResolver rfcResolver;

    @Override
    public List<RFCCommonCodeVO> getCommonCode(RFCParam param) {
        return rfcResolver.getExecutor(new CommonCodeRFC()).execute(param, RFCCommonCodeVO[].class);
    }

    @Override
    public List<RFCAssetsVO> getAssetsMaster() {
        return getAssetsMaster(new RFCParam<>());
    }

    public List<RFCAssetsVO> getAssetsMaster(RFCParam<RFCParamOfAssets, String> param) {
        return rfcResolver.getExecutor(new AssetsRFC()).execute(param, RFCAssetsVO[].class);
    }

    public List<RFCAssetsDepreciationVO> getAssetsDepreciation(RFCParam<RFCParamOfAssetsDepreciation, String> param) {
        return rfcResolver.getExecutor(new AssetsDepreciationRFC()).execute(param, RFCAssetsDepreciationVO[].class);
    }

    @Override
    public List<RFCBusinessPartnerVO> getBusinessPartner(RFCParam<RFCParamOfBusinessPartner, String> param) {
        return rfcResolver.getExecutor(new BusinessPartnerRFC()).execute(param, RFCBusinessPartnerVO[].class);
    }
}