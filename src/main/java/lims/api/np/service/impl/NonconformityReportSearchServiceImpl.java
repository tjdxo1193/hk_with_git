package lims.api.np.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.np.dao.NonconformityReportSearchDao;
import lims.api.np.enums.NonCfmProcess;
import lims.api.np.service.NonconformityReportSearchService;
import lims.api.np.vo.NonconformityReportSearchVO;
import lims.api.ts.enums.TestJudgement;
import lims.api.ts.enums.TestProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NonconformityReportSearchServiceImpl implements NonconformityReportSearchService {

    private final NonconformityReportSearchDao dao;
    private final ApproveService approveService;

    @Override
    public List<NonconformityReportSearchVO> findAll(NonconformityReportSearchVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        param.setSytJdg(TestJudgement.UNSUITABLE.getJudgementCode());
        return dao.findAll(param);
    }

    @Override
    public List<NonconformityReportSearchVO> findResultItem(NonconformityReportSearchVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return dao.findResultItem(param);
    }
}
