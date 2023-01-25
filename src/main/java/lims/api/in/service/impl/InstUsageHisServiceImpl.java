package lims.api.in.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.FileService;
import lims.api.in.dao.InstUsageHisDao;
import lims.api.in.service.InstUsageHisService;
import lims.api.in.vo.InstUsageHisVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstUsageHisServiceImpl implements InstUsageHisService {
    private final InstUsageHisDao dao;
    private final FileService fileService;

    @Override
    public List<InstUsageHisVO> find(InstUsageHisVO param) {
        if (param.getUseDsList().size() > 0) {
            param.setUseStrDs(param.getUseDsList().get(0));
            param.setUseEndDs(param.getUseDsList().get(1));
        }
        return dao.find(param);
    }

    @Override
    public List<InstUsageHisVO> findInstrument(InstUsageHisVO param) {
        return dao.findInstrument(param);
    }

    @Override
    public void create(InstUsageHisVO param) {
        int result = 0;
        if (param.getUseDsList().size() > 0) {
            param.setUseStrDs(param.getUseDsList().get(0));
            param.setUseEndDs(param.getUseDsList().get(1));
        }
        result += dao.create(param);
        if (result < 1) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(InstUsageHisVO param) {
        int result = 0;
        if (param.getUseDsList().size() > 0) {
            param.setUseStrDs(param.getUseDsList().get(0));
            param.setUseEndDs(param.getUseDsList().get(1));
        }
        result += dao.update(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(InstUsageHisVO param) {
        int result = 0;
        result += dao.delete(param);
        if (result < 1) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public Integer updateFile(InstUsageHisVO param) {
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
}
