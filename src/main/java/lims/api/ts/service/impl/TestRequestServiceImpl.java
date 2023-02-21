package lims.api.ts.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ts.dao.TestRequestDao;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.service.TestRequestService;
import lims.api.ts.vo.TestRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestRequestServiceImpl implements TestRequestService {

    private final TestRequestDao dao;

    @Override
    public List<TestRequestVO> getTestRequestList(TestRequestVO request) {
        request.setAnsProcCd(TestProcess.TEST_REQUEST.getProcessCode());
        return dao.getTestRequestList(request);
    }

    @Override
    public List<TestRequestVO> getTestList(TestRequestVO request) {
        request.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return dao.getTestList(request);
    }

    @Override
    public void requestRegist(TestRequestVO request) {
        request.setAnsProcCd(TestProcess.TEST_REQUEST.getProcessCode());
        request.setAnsIdx(dao.getAnsIdx(request));
        int result = dao.requestRegist(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void request(List<TestRequestVO> request) {
        int result = 0;
        for(TestRequestVO item : request) {
            item.setAnsProcCd(TestProcess.TEST_RECEIPT.getProcessCode());
            dao.insertTestRst(item); //시험결과
            result += dao.request(item);
        }
        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void save(TestRequestVO request) {
        int result = dao.save(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
