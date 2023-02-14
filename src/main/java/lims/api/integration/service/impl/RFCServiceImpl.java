package lims.api.integration.service.impl;

import lims.api.integration.domain.rfc.AssetsDepreciationRFC;
import lims.api.integration.domain.rfc.AssetsRFC;
import lims.api.integration.domain.rfc.CommonCodeRFC;
import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.service.RFCService;
import lims.api.integration.vo.rfc.RFCAssetsDepreciationVO;
import lims.api.integration.vo.rfc.RFCAssetsVO;
import lims.api.integration.vo.rfc.RFCCommonCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RFCServiceImpl implements RFCService {

    private final RFCResolver rfcResolver;

    @Override
    public List<RFCCommonCodeVO> getCommonCode(RFCParam param) {
        return rfcResolver.getExecutor(new CommonCodeRFC()).execute(param, RFCCommonCodeVO[].class);
    }

    @Override
    public List<RFCAssetsVO> getAssets(RFCParam param) {
        return rfcResolver.getExecutor(new AssetsRFC()).execute(param, RFCAssetsVO[].class);
    }

    @Override
    public List<RFCAssetsDepreciationVO> getAssetsDepreciation(RFCParam param) {
        return rfcResolver.getExecutor(new AssetsDepreciationRFC()).execute(param, RFCAssetsDepreciationVO[].class);
    }
}