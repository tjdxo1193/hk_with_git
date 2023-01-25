package lims.api.ts.dao;

import lims.api.ts.vo.SrmMesModalVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SrmMesModalDao {
    List<SrmMesModalVO> getSrmFinalOrderList(SrmMesModalVO request);

    List<SrmMesModalVO> getMesFinalOrderList(SrmMesModalVO request);

    List<SrmMesModalVO> getRelapsePrevList(SrmMesModalVO request);

    List<SrmMesModalVO> getSrmReportList(SrmMesModalVO request);

    List<SrmMesModalVO> getPackingSpecList(SrmMesModalVO request);
}
