package lims.api.np.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.np.dao.NonconformityReportSearchDao;
import lims.api.np.service.NonconformityReportSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NonconformityReportSearchServiceImpl implements NonconformityReportSearchService {

    private final NonconformityReportSearchDao dao;
    private final ApproveService approveService;
}
