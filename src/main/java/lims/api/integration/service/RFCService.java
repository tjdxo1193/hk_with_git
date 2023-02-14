package lims.api.integration.service;

import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.vo.rfc.RFCAssetsDepreciationVO;
import lims.api.integration.vo.rfc.RFCAssetsVO;
import lims.api.integration.vo.rfc.RFCCommonCodeVO;

import java.util.List;
import java.util.Map;

public interface RFCService {

    List<RFCCommonCodeVO> getCommonCode(RFCParam param);

    List<RFCAssetsVO> getAssets(RFCParam param);

    List<RFCAssetsDepreciationVO> getAssetsDepreciation(RFCParam param);

}