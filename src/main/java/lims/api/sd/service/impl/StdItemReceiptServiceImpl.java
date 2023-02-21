package lims.api.sd.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.FileService;
import lims.api.sd.dao.StdItemReceiptDao;
import lims.api.sd.enums.StandardMaterialProcess;
import lims.api.sd.service.StdItemReceiptService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StdItemReceiptServiceImpl implements StdItemReceiptService {

    private final StdItemReceiptDao dao;
    private final ApproveService approveService;
    private final FileService fileService;

    @Override
    public List<StandardMaterialVO> findAll(StandardMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public int create(StandardMaterialVO param) {
        param.setEtrProcCd(StandardMaterialProcess.SAVE.getProcessCode());
        int result = dao.create(param);
        result += dao.createStandard(param);

        if(result != 2) {
            throw new NoCreatedDataException();
        }

        return param.getRitmEtrIdx();
    }

    @Override
    public void update(StandardMaterialVO param) {
        int result = dao.update(param);
        result += dao.updateStandard(param);

        if(result != 2) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void delete(StandardMaterialVO param) {
        int result = dao.delete(param);

        if(result == 0) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public void requestApprove(List<StandardMaterialVO> list) {
        int result = 0;

        for(StandardMaterialVO row : list) {
            row.getApproveInfo().setAprIdx(row.getEtrReqAprIdx());
            int reqIdx = approveService.requestApprove(row.getApproveInfo());
            row.setEtrReqAprIdx(reqIdx);
            row.setEtrProcCd(StandardMaterialProcess.APPROVE_REQUEST.getProcessCode());
            result += dao.requestApprove(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public int savedFile(StandardMaterialVO param) {
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
