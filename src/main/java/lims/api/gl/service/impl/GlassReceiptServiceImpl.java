package lims.api.gl.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.FileService;
import lims.api.gl.dao.GlassReceiptDao;
import lims.api.gl.enums.GlassMaterialProcess;
import lims.api.gl.service.GlassReceiptService;
import lims.api.gl.vo.GlassMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassReceiptServiceImpl implements GlassReceiptService {
    
    private final GlassReceiptDao dao;
    private final FileService fileService;

    @Override
    public List<GlassMaterialVO> findAll(GlassMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public int create(GlassMaterialVO param) {
        param.setEtrProcCd(GlassMaterialProcess.SAVE.getProcessCode());
        int result = dao.create(param);
        result += dao.createGlass(param);

        if(result != 2) {
            throw new NoCreatedDataException();
        }

        return param.getRitmEtrIdx();
    }

    @Override
    public void update(GlassMaterialVO param) {
        int result = dao.update(param);
        result += dao.updateGlass(param);

        if(result != 2) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void delete(GlassMaterialVO param) {
        int result = dao.delete(param);

        if(result == 0) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public int savedFile(GlassMaterialVO param) {
        if(param.getFileIdx() == 0) {
            FileKey fileKey = fileService.save(param.getAddedFiles());
            param.setFileIdx(fileKey.getFileIdx());
        }else {
            for (FileKey removedFileId : param.getRemovedFileIds()) {
                fileService.deleteFile(removedFileId);
            }
            fileService.save(param.getAddedFiles(), param.getFileIdx());
        }

        int result = dao.savedFile(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        return param.getFileIdx();
    }
}
