package lims.api.ts.service;

import lims.api.ts.vo.TestRequestVO;

import java.util.List;

public interface TestRequestService {
    List<TestRequestVO> getTestRequestList(TestRequestVO request);
    List<TestRequestVO> getTestList(TestRequestVO request);

    void requestRegist(TestRequestVO request);
    void request(List<TestRequestVO> request);
    void save(TestRequestVO request);
}
