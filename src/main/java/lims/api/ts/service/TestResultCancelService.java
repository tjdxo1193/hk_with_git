package lims.api.ts.service;

import lims.api.ts.vo.TestResultCancelVO;

import java.util.List;

public interface TestResultCancelService {
    List<TestResultCancelVO> findAll(TestResultCancelVO param);
    List<TestResultCancelVO> findResultItem(Integer ansIdx);
    void testCancel(List<TestResultCancelVO> list);
    void hold(List<TestResultCancelVO> list);
    void holdOff(List<TestResultCancelVO> list);
    int savedFile(TestResultCancelVO param);
}
