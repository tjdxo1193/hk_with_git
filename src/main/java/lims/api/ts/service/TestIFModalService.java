package lims.api.ts.service;

import lims.api.ts.vo.TestIFModalVO;

import java.io.IOException;
import java.util.List;

public interface TestIFModalService {
    List<TestIFModalVO> getSrmFinalOrderList(TestIFModalVO request);

    List<TestIFModalVO> getMesFinalOrderList(TestIFModalVO request);

    List<TestIFModalVO> getPrvRcrReportList(TestIFModalVO request) throws IOException;

    List<TestIFModalVO> getSrmReportList(TestIFModalVO request) throws IOException;

    List<TestIFModalVO> getPackingSpecList(TestIFModalVO request) throws IOException;

    List<TestIFModalVO> getInpPerformanceList(TestIFModalVO request);

    void savePrvRcrReport(TestIFModalVO request);
}
