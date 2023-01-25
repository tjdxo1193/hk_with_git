package lims.api.sc.service.impl;

import lims.api.sc.dao.InspectProductionPerformanceDao;
import lims.api.sc.service.InspectProductionPerformanceService;
import lims.api.sc.vo.InspectProductionPerformanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectProductionPerformanceServiceImpl implements InspectProductionPerformanceService {
    private final InspectProductionPerformanceDao dao;

    @Override
    public List<InspectProductionPerformanceVO> find(InspectProductionPerformanceVO param) {
        return dao.find(param);
    }
}
