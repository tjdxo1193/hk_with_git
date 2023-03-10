package lims.api.in.service;

import lims.api.common.vo.RFCAssetDepreciationRequestVO;
import lims.api.common.vo.RFCAssetRequestVO;
import lims.api.in.vo.InstManageVO;
import lims.api.integration.vo.rfc.RFCAssetsDepreciationVO;
import lims.api.integration.vo.rfc.RFCAssetsVO;

import java.util.List;

public interface InstManageService {
    List<InstManageVO> find(InstManageVO param);

    List<InstManageVO> findAccessory(InstManageVO param);

    void create(InstManageVO param);

    void update(InstManageVO param);

    void delete(InstManageVO param);

    Integer updateFile(InstManageVO param);

    List<RFCAssetsVO> getAssetsMaster(RFCAssetRequestVO param);

    List<RFCAssetsDepreciationVO> getAssetsDepreciation(RFCAssetDepreciationRequestVO param);
}