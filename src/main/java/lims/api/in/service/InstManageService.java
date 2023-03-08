package lims.api.in.service;

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

    List<RFCAssetsVO> getAssetsMasterToModal(InstManageVO param);

    List<RFCAssetsDepreciationVO> getAssetsDepreciationToModal(InstManageVO param);
}
