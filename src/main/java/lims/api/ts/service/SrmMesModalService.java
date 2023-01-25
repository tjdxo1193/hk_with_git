package lims.api.ts.service;

import lims.api.ts.vo.SrmMesModalVO;

import java.util.List;

public interface SrmMesModalService {
    List<SrmMesModalVO> getSrmFinalOrderList(SrmMesModalVO request);

    List<SrmMesModalVO> getMesFinalOrderList(SrmMesModalVO request);

    List<SrmMesModalVO> getRelapsePrevList(SrmMesModalVO request);

    List<SrmMesModalVO> getSrmReportList(SrmMesModalVO request);

    List<SrmMesModalVO> getPackingSpecList(SrmMesModalVO request);
}
