package lims.api.ts.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.FileService;
import lims.api.ts.dao.TestCollectionDao;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.service.TestCollectionService;
import lims.api.ts.vo.TestCollectionVO;
import lims.api.ts.vo.TestResultInputVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCollectionServiceImpl implements TestCollectionService {

    private final TestCollectionDao dao;
    private final ApproveService approveService;
    private final FileService fileService;

    @Override
    public List<TestCollectionVO> getTestCollectionList(TestCollectionVO request) {
        return dao.getTestCollectionList(request);
    }

    @Override
    public void collect(TestCollectionVO request) {
        request.setAnsProcCd(TestProcess.TEST_INSTRUCTION.getProcessCode());
        int assAprReqIdx = approveService.requestApprove(request.getApproveInfo());
        request.setAssAprReqIdx(assAprReqIdx);
        int result = dao.collect(request);
        dao.updateInfo(request);
        //검체관리 추가
        if(request.getSmpVolAns() != 0 || request.getSmpVolEtc() != 0 || request.getSmpVolStrg() != 0){
            insertSampleInfo(request);
        }
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    public void insertSampleInfo(TestCollectionVO request) {
        TestCollectionVO item = dao.getSmpInfo(request);
        int result = 0;
        List<TestCollectionVO> list = new ArrayList<>();
        if(request.getSmpVolAns() != 0){
            item.setSmpDivCd(TestProcess.TEST_INSTRUCTION.getProcessCode());
            item.setMngSmpVol(request.getSmpVolAns());
            list.add(item);
        }if(request.getSmpVolEtc() != 0){
            item.setSmpDivCd(TestProcess.TEST_INSTRUCTION.getProcessCode());
            item.setMngSmpVol(request.getSmpVolAns());
            list.add(item);
        }if(request.getSmpVolStrg() != 0){
            item.setSmpDivCd(TestProcess.TEST_INSTRUCTION.getProcessCode());
            item.setMngSmpVol(request.getSmpVolAns());
            list.add(item);
        }
        for(TestCollectionVO row : list) {
            int smpMngIdx = dao.getSmpMngIdx(request);
            row.setSmpMngIdx(smpMngIdx);
            result += dao.insertSmpInfo(row);
        }
        if (result != list.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void save(TestCollectionVO request) {
        int result = dao.updateInfo(request);
        dao.save(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public int saveFile(TestCollectionVO request) {
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

}
