package lims.api.in.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.FileService;
import lims.api.common.vo.RFCAssetDepreciationRequestVO;
import lims.api.common.vo.RFCAssetRequestVO;
import lims.api.in.dao.InstManageDao;
import lims.api.in.service.InstManageService;
import lims.api.in.vo.InstManageVO;
import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.enums.rfc.RFCParamOfAssets;
import lims.api.integration.enums.rfc.RFCParamOfAssetsDepreciation;
import lims.api.integration.service.RFCService;
import lims.api.integration.vo.rfc.RFCAssetsDepreciationVO;
import lims.api.integration.vo.rfc.RFCAssetsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstManageServiceImpl implements InstManageService {
    private final InstManageDao dao;
    private final FileService fileService;
    private final RFCService rfcService;

    @Override
    public List<InstManageVO> find(InstManageVO param) {
        return dao.find(param);
    }

    @Override
    public List<InstManageVO> findAccessory(InstManageVO param) {
        return dao.findAccessory(param);
    }

    @Override
    public void create(InstManageVO param) {
        int result = 0;
        result += dao.create(param);
        if (param.getAddedAcsrList().size() > 0) {
            List<InstManageVO> addedAccessories = setAccessories(param.getAddedAcsrList(), param.getPlntCd(), param.getEqmCd());
            addedAccessories.forEach(dao::createAccessory);
        }
        if (result < 1) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(InstManageVO param) {
        int result = 0;
        String plntCd = param.getPlntCd();
        String eqmCd = param.getEqmCd();
        result += dao.update(param);
        if (param.getAddedAcsrList().size() > 0) {
            List<InstManageVO> addedAccessories = setAccessories(param.getAddedAcsrList(), plntCd, eqmCd);
            addedAccessories.forEach(dao::createAccessory);
        }
        if (param.getEditedAcsrList().size() > 0) {
            List<InstManageVO> editedAccessories = setAccessories(param.getEditedAcsrList(), plntCd, eqmCd);
            editedAccessories.forEach(dao::updateAccessory);
        }
        if (param.getRemovedAcsrList().size() > 0) {
            List<InstManageVO> removedAccessories = setAccessories(param.getRemovedAcsrList(), plntCd, eqmCd);
            removedAccessories.forEach(dao::deleteAccessory);
        }
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(InstManageVO param) {
        int result = 0;
        result += dao.delete(param);
        result += dao.deleteAllAccessory(param);
        if (result < 1) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public Integer updateFile(InstManageVO param) {
        if (param.getEqmFileIdx() < 1) {
            FileKey fileKey = fileService.save(param.getAddedFiles());
            param.setEqmFileIdx(fileKey.getFileIdx());
        } else {
            for (FileKey removedFileId : param.getRemovedFileIds()) {
                fileService.deleteFile(removedFileId);
            }
            fileService.save(param.getAddedFiles(), param.getEqmFileIdx());
        }
        int result = 0;
        result += dao.updateFile(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
        return param.getEqmFileIdx();
    }

    @Override
    public List<RFCAssetsVO> getAssetsMaster(RFCAssetRequestVO param) {
        RFCParam<RFCParamOfAssets, String> rfcParam = new RFCParam<>();
        List<String> dates = param.getSearchPrtDt();
        if (dates.size() != 2) {
            throw new RuntimeException("Date range parameter length must be 2.");
        }
        rfcParam.put(RFCParamOfAssets.IV_GETDAT_FROM, dates.get(0).replaceAll("-", ""));
        rfcParam.put(RFCParamOfAssets.IV_GETDAT_TO, dates.get(1).replaceAll("-", ""));
        return rfcService.getAssetsMaster(rfcParam);
    }

    @Override
    public List<RFCAssetsDepreciationVO> getAssetsDepreciation(RFCAssetDepreciationRequestVO param) {
        RFCParam<RFCParamOfAssetsDepreciation, String> rfcParam = new RFCParam<>();
        rfcParam.put(RFCParamOfAssetsDepreciation.I_ANLKL, param.getAnlkl());   // 자산클래스
        rfcParam.put(RFCParamOfAssetsDepreciation.I_BZDAT, param.getBzdat());   // 자산기준일
        return rfcService.getAssetsDepreciation(rfcParam);
    }

    private List<InstManageVO> setAccessories(List<InstManageVO> params, String plntCd, String eqmCd) {
        params.forEach(accessory -> {
            accessory.setPlntCd(plntCd);
            accessory.setEqmCd(eqmCd);
        });
        return params;
    }
}