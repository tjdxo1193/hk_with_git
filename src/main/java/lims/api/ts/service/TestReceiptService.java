package lims.api.ts.service;

import lims.api.ts.vo.TestReceiptVO;

import java.util.List;

public interface TestReceiptService {
    List<TestReceiptVO> getTestReceiptList(TestReceiptVO request);
    List<TestReceiptVO> getTestAitm(TestReceiptVO request);
    void receipt(List<TestReceiptVO> request);
    List<TestReceiptVO> getNonconformityTestList(TestReceiptVO request);
}
