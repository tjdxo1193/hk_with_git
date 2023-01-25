package lims.api.integration.dao;

import lims.api.integration.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SRMMasterDao {

    List<SRMFinalOrderVO> findFinalOrder();

    int createFinalOrder(SRMFinalOrderVO param);


    int nextSeqInReoccurPreventionReportByBatchNo(String batchNo);

    int createReoccurPreventionReport(SRMReoccurPreventReportVO param);


    int nextSeqInConsignSupplierReport(String batchNo);

    int createConsignSupplierReport(SRMSupplierReportVO param);

}