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
    public List<RFCAssetsVO> getAssets() {
        return getAssets(new RFCParam<>());
    }

    @Override
    public List<RFCAssetsVO> getAssets(RFCParam<RFCParamOfAssets, String> param) {
        Set<RFCAssetsVO> result = new HashSet<>();

        List<RFCAssetsVO> assetsList = getAssetsMaster(param);

        RFCParam<RFCParamOfAssetsDepreciation, String> depreciationParam;
        for (RFCAssetsVO assets : assetsList) {
            depreciationParam = new RFCParam<>();
            depreciationParam.put(RFCParamOfAssetsDepreciation.I_ANLKL, assets.getAnlkl());
            List<RFCAssetsDepreciationVO> depreciations = getAssetsDepreciation(depreciationParam);

            for (RFCAssetsDepreciationVO depreciation : depreciations) {
                RFCAssetsVO info = new RFCAssetsVO();
                BeanUtils.copyProperties(assets, info);
                info.setKansw(depreciation.getKansw());
                info.setKumafa(depreciation.getKumafa());
                info.setBzdat(depreciation.getBzdat());
                result.add(info);
            }
        }
        return new ArrayList<>(result);
    }

    private List<RFCAssetsVO> getAssetsMaster(RFCParam<RFCParamOfAssets, String> param) {
        return rfcResolver.getExecutor(new AssetsRFC()).execute(param, RFCAssetsVO[].class);
    }

    private List<RFCAssetsDepreciationVO> getAssetsDepreciation(RFCParam<RFCParamOfAssetsDepreciation, String> param) {
        return rfcResolver.getExecutor(new AssetsDepreciationRFC()).execute(param, RFCAssetsDepreciationVO[].class);
    }

    @Override
    public List<RFCBusinessPartnerVO> getBusinessPartner(RFCParam<RFCParamOfBusinessPartner, String> param) {
        return rfcResolver.getExecutor(new BusinessPartnerRFC()).execute(param, RFCBusinessPartnerVO[].class);
    }
}