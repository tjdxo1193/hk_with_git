package lims.api.np.service;

import lims.api.np.vo.NonconformityReportWrtVO;

import java.util.List;

public interface NonconformityReportWrtService {
    List<NonconformityReportWrtVO> findAll(NonconformityReportWrtVO param);

    List<NonconformityReportWrtVO> findResultItem(NonconformityReportWrtVO param);

    void save(NonconformityReportWrtVO param);
    void request(NonconformityReportWrtVO param);
}
