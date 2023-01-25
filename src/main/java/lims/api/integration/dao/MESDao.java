package lims.api.integration.dao;

import lims.api.integration.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MESDao {

    List<MESFinalOrderVO> findFinalOrderByDegree(int degree);

    int nextDegreeInFinalOrder();

    int nextIdxInFinalOrder();

    int createFinalOrder(MESFinalOrderVO vo);


    int createTestStatus(MESSendVO.TestStatus vo);


    int createTestResult(MESSendVO.TestResult vo);


    List<MESPackageSpecReportVO> findPackageSpecByDegree(int degree);

    int nextDegreeInPackageSpec();

    int nextIdxInPackageSpec();

    int createPackageSpec(MESPackageSpecReportVO vo);


    int nextDegreeInNonCfmReport();

    int nextIdxInNonCfmReport();

    int createNonCfmReport(MESSendVO.NonCfmReport vo);

    int updateTrsStatusOfNonCfmReport(MESSendVO.NonCfmReport vo);

}