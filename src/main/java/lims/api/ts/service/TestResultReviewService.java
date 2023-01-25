package lims.api.ts.service;

import lims.api.ts.vo.TestResultReviewVO;

import java.util.List;

public interface TestResultReviewService {
    List<TestResultReviewVO> findAll(TestResultReviewVO param);
    List<TestResultReviewVO> findResultItem(Integer ansIdx);
    void completedReview(TestResultReviewVO param);
    void requestHold(TestResultReviewVO param);
    void requestRejection(TestResultReviewVO param);
    int savedFile(TestResultReviewVO param);
}
