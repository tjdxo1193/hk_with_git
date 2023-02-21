package lims.api.re.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.FileService;
import lims.api.re.dao.ReagentReceiptDao;
import lims.api.re.enums.ReagentMaterialProcess;
import lims.api.re.service.ReagentReceiptService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReagentReceiptServiceImpl implements ReagentReceiptService {

    private final ReagentReceiptDao dao;
    private final FileService fileService;

    @Override
    public List<ReagentMaterialVO> findAll(ReagentMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public int create(ReagentMaterialVO param) {
        param.setEtrProcCd(ReagentMaterialProcess.SAVE.getProcessCode());
        int result = dao.create(param);
        result += dao.createReagent(param);

        if(result != 2) {
            throw new NoCreatedDataException();
        }

        return param.getRitmEtrIdx();
    }

    @Override
    public void update(ReagentMaterialVO param) {
        int result = dao.update(param);
        result += dao.updateReagent(param);

        if(result != 2) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void delete(ReagentMaterialVO param) {
        int result = dao.delete(param);

        if(result == 0) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public int savedFile(ReagentMaterialVO param) {
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
