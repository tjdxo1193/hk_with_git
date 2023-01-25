package lims.api.sc.service.impl;

import lims.api.sc.dao.InspectPurchasingPerformanceDao;
import lims.api.sc.service.InspectPurchasingPerformanceService;
import lims.api.sc.vo.InspectPurchasingPerformanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectPurchasingPerformanceServiceImpl implements InspectPurchasingPerformanceService {
    private final InspectPurchasingPerformanceDao dao;

    @Override
    public List<InspectPurchasingPerformanceVO> find(InspectPurchasingPerformanceVO param) {
        return dao.find(param);
    }

    @Override
    public List<InspectPurchasingPerformanceVO> findRequest(InspectPurchasingPerformanceVO param) {
        return dao.findRequest(param);
    }
}
