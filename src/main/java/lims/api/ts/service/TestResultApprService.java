package lims.api.ts.service;

import lims.api.ts.vo.TestResultApprVO;

import java.util.List;

public interface TestResultApprService {
    List<TestResultApprVO> findAll(TestResultApprVO param);
    List<TestResultApprVO> findResultItem(Integer ansIdx);
    void approve(List<TestResultApprVO> list);
    void requestHold(List<TestResultApprVO> list);
    void requestRejection(List<TestResultApprVO> list);
    void save(TestResultApprVO request);
}
