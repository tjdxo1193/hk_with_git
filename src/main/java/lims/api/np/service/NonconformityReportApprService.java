package lims.api.np.service;

import lims.api.np.vo.NonconformityReportApprVO;

import java.util.List;

public interface NonconformityReportApprService {
    List<NonconformityReportApprVO> findAll(NonconformityReportApprVO param);

    List<NonconformityReportApprVO> findResultItem(NonconformityReportApprVO param);

    void approve(NonconformityReportApprVO param);

    void reject(NonconformityReportApprVO param);
}
