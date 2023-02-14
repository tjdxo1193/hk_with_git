package lims.api.sc.service;

import lims.api.sc.vo.InspectProductionPerformanceVO;

import java.util.List;

public interface InspectProductionPerformanceService {
    List<InspectProductionPerformanceVO> find(InspectProductionPerformanceVO param);
    List<InspectProductionPerformanceVO> findDetail(String mtrCd, String pdtOrderNo, String batchNo, String etrDt);
    List<InspectProductionPerformanceVO> getRecord(Integer ispPdtPfaIdx);
    String send(List<InspectProductionPerformanceVO> list);
}
