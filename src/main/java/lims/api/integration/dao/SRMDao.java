package lims.api.integration.dao;

import lims.api.integration.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SRMDao {

    List<SRMFinalOrderVO> findFinalOrderByDegree(int degree);

    int nextDegreeInFinalOrder();

    int nextIdxInFinalOrder();

    int createFinalOrder(SRMFinalOrderVO vo);


    int createTestStatus(SRMSendVO.TestStatus vo);


    int createTestResult(SRMSendVO.TestResult vo);


    List<SRMReoccurPreventReportVO> findReoccurPreventionReportByDegree(int degree);

    int nextDegreeInReoccurPreventionReport();

    int nextIdxInReoccurPreventionReport();

    int createReoccurPreventionReport(SRMReoccurPreventReportVO param);


    List<SRMSupplierReportVO> findConsignSupplierReportByDegree(int degree);

    int nextDegreeInConsignSupplierReport();

    int nextIdxInConsignSupplierReport();

    int createConsignSupplierReport(SRMSupplierReportVO vo);


    int nextDegreeInNonCfmReport();

    int nextIdxInNonCfmReport();

    int createNonCfmReport(SRMSendVO.NonCfmReport vo);

    int updateTrsStatusOfNonCfmReport(SRMSendVO.NonCfmReport vo);

}