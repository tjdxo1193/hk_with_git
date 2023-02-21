package lims.api.np.service;

import lims.api.np.vo.NonconformityReportSearchVO;

import java.util.List;

public interface NonconformityReportSearchService {
    List<NonconformityReportSearchVO> findAll(NonconformityReportSearchVO param);

    List<NonconformityReportSearchVO> findResultItem(NonconformityReportSearchVO param);
}
