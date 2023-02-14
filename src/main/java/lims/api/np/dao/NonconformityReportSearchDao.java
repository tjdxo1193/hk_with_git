package lims.api.np.dao;

import lims.api.np.vo.NonconformityReportSearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NonconformityReportSearchDao {
    List<NonconformityReportSearchVO> findAll(NonconformityReportSearchVO param);

    List<NonconformityReportSearchVO> findResultItem(NonconformityReportSearchVO param);
}
