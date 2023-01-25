package lims.api.ts.service.impl;

import lims.api.ts.dao.SrmMesModalDao;
import lims.api.ts.service.SrmMesModalService;
import lims.api.ts.vo.SrmMesModalVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SrmMesModalServiceImpl implements SrmMesModalService {

    private final SrmMesModalDao dao;

    @Override
    public List<SrmMesModalVO> getSrmFinalOrderList(SrmMesModalVO request) {
        return dao.getSrmFinalOrderList(request);
    }

    @Override
    public List<SrmMesModalVO> getMesFinalOrderList(SrmMesModalVO request) {
        return dao.getMesFinalOrderList(request);
    }

    @Override
    public List<SrmMesModalVO> getRelapsePrevList(SrmMesModalVO request) {
        return dao.getRelapsePrevList(request);
    }

    @Override
    public List<SrmMesModalVO> getSrmReportList(SrmMesModalVO request) {
        return dao.getSrmReportList(request);
    }

    @Override
    public List<SrmMesModalVO> getPackingSpecList(SrmMesModalVO request) {
        return dao.getPackingSpecList(request);
    }
}
