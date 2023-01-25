package lims.api.an.service.Impl;

import lims.api.an.dao.AnalColReceiptDao;
import lims.api.an.enums.AnalColMaterialProcess;
import lims.api.an.service.AnalColReceiptService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalColReceiptServiceImpl implements AnalColReceiptService {

    private final AnalColReceiptDao dao;
    private final FileService fileService;

    @Override
    public List<AnalColMaterialVO> findAll(AnalColMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public int create(AnalColMaterialVO param) {
        param.setEtrProcCd(AnalColMaterialProcess.SAVE.getProcessCode());
        int result = dao.create(param);
        result += dao.createAnalCol(param);

        if(result != 2) {
            throw new NoCreatedDataException();
        }

        return param.getRitmEtrIdx();
    }

    @Override
    public void update(AnalColMaterialVO param) {
        int result = dao.update(param);
        result += dao.updateAnalCol(param);

        if(result != 2) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void delete(AnalColMaterialVO param) {
        int result = dao.delete(param);

        if(result == 0) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public int savedFile(AnalColMaterialVO param) {
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
