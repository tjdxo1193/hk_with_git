package lims.api.ts.service.impl;

import lims.api.ts.dao.TestIFModalDao;
import lims.api.ts.service.TestIFModalService;
import lims.api.ts.vo.TestIFModalVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestIFModalServiceImpl implements TestIFModalService {

    private final TestIFModalDao dao;

    @Override
    public List<TestIFModalVO> getSrmFinalOrderList(TestIFModalVO request) {
        return dao.getSrmFinalOrderList(request);
    }

    @Override
    public List<TestIFModalVO> getMesFinalOrderList(TestIFModalVO request) {
        return dao.getMesFinalOrderList(request);
    }

    @Override
    public List<TestIFModalVO> getRelapsePrevList(TestIFModalVO request) {
        return dao.getRelapsePrevList(request);
    }

    @Override
    public List<TestIFModalVO> getSrmReportList(TestIFModalVO request) {
        return dao.getSrmReportList(request);
    }

    @Override
    public List<TestIFModalVO> getPackingSpecList(TestIFModalVO request) {
        return dao.getPackingSpecList(request);
    }

    @Override
    public List<TestIFModalVO> getInpPerformanceList(TestIFModalVO request) {
        return dao.getInpPerformanceList(request);
    }
}
