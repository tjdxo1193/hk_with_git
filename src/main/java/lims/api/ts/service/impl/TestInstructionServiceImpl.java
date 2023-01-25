package lims.api.ts.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.FileService;
import lims.api.ts.dao.TestInstructionDao;
import lims.api.ts.service.TestInstructionService;
import lims.api.ts.vo.TestInstructionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestInstructionServiceImpl implements TestInstructionService {

    private final TestInstructionDao dao;
    private final FileService fileService;
    private final ApproveService approveService;

    @Override
    public List<TestInstructionVO> getTestInstructList(TestInstructionVO request) {
        return dao.getTestInstructList(request);
    }

    @Override
    public List<TestInstructionVO> getTestAitm(TestInstructionVO request) {
        return dao.getTestAitm(request);
    }

    @Override
    public void instruct(List<TestInstructionVO> request) {
        int result = 0;
        int assNo = 1;
        for (TestInstructionVO item : request) {
            approveService.approve(item.getAssAprReqIdx());
            item.setAssNo(assNo);
            result += dao.instruct(item);
            assNo++;
        }
        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public int saveFile(TestInstructionVO request) {
        if(request.getFileIdx() == 0) {
            FileKey fileKey = fileService.save(request.getAddedFiles());
            request.setFileIdx(fileKey.getFileIdx());
        }else {
            for (FileKey removedFileId : request.getRemovedFileIds()) {
                fileService.deleteFile(removedFileId);
            }
            fileService.save(request.getAddedFiles(), request.getFileIdx());
        }

        int result = dao.savedRequestFile(request);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        return request.getFileIdx();
    }

    @Override
    public void deleteRst(List<TestInstructionVO> request) {
        int result = 0;
        for (TestInstructionVO item : request) {
            result += dao.deleteRst(item);
        }
        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }
    }
}
