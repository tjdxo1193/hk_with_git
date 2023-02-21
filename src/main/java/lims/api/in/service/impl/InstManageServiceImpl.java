package lims.api.in.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.FileService;
import lims.api.in.dao.InstManageDao;
import lims.api.in.service.InstManageService;
import lims.api.in.vo.InstManageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstManageServiceImpl implements InstManageService {
    private final InstManageDao dao;
    private final FileService fileService;

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

    private List<InstManageVO> setAccessories(List<InstManageVO> params, String plntCd, String eqmCd) {
        params.forEach(accessory -> {
            accessory.setPlntCd(plntCd);
            accessory.setEqmCd(eqmCd);
        });
        return params;
    }
}
