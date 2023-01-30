package lims.api.ts.service;

import lims.api.ts.vo.TestIFModalVO;

import java.util.List;

public interface TestIFModalService {
    List<TestIFModalVO> getSrmFinalOrderList(TestIFModalVO request);

    List<TestIFModalVO> getMesFinalOrderList(TestIFModalVO request);

    List<TestIFModalVO> getRelapsePrevList(TestIFModalVO request);

    List<TestIFModalVO> getSrmReportList(TestIFModalVO request);

    List<TestIFModalVO> getPackingSpecList(TestIFModalVO request);

    List<TestIFModalVO> getInpPerformanceList(TestIFModalVO request);
}
