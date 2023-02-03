package lims.api.ts.service;

import lims.api.ts.vo.TestResultInputVO;

import java.util.List;

public interface TestResultInputService {
    List<TestResultInputVO> findAll(TestResultInputVO param);
    List<TestResultInputVO> findResultItem(Integer ansIdx);
    List<TestResultInputVO> resultHistory(TestResultInputVO param);
    void save(TestResultInputVO param);
    void requestReview(TestResultInputVO param);
    void publishEvent(TestResultInputVO param);
    int savedFile(TestResultInputVO param);
    void requestHold(TestResultInputVO param);
}
