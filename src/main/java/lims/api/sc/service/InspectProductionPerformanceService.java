package lims.api.sc.service;

import lims.api.sc.vo.InspectProductionPerformanceVO;

import java.util.List;

public interface InspectProductionPerformanceService {
    List<InspectProductionPerformanceVO> find(InspectProductionPerformanceVO param);

    List<InspectProductionPerformanceVO> findDetail(InspectProductionPerformanceVO param);
}
