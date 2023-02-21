package lims.api.ts.dao;

import lims.api.ts.vo.TestIFModalVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestIFModalDao {
    List<TestIFModalVO> getSrmFinalOrderList(TestIFModalVO request);

    List<TestIFModalVO> getMesFinalOrderList(TestIFModalVO request);

    List<TestIFModalVO> getPrvRcrReportList(TestIFModalVO request);

    List<TestIFModalVO> getSrmReportList(TestIFModalVO request);

    List<TestIFModalVO> getPackingSpecList(TestIFModalVO request);

    List<TestIFModalVO> getInpPerformanceList(TestIFModalVO request);
}
