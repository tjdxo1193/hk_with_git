package lims.api.np.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.np.dao.PreventRecurrenceApprDao;
import lims.api.np.enums.NonCfmProcess;
import lims.api.np.service.PreventRecurrenceApprService;
import lims.api.np.vo.PreventRecurrenceApprVO;
import lims.api.ts.enums.TestJudgement;
import lims.api.ts.enums.TestProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreventRecurrenceApprServiceImpl implements PreventRecurrenceApprService {

    private final PreventRecurrenceApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<PreventRecurrenceApprVO> findAll(PreventRecurrenceApprVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        param.setSytJdg(TestJudgement.UNSUITABLE.getJudgementCode());
        param.setNonCfmProcCd(NonCfmProcess.PRV_RCR_REPORT_APPROVE.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public List<PreventRecurrenceApprVO> findResultItem(PreventRecurrenceApprVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return dao.findResultItem(param);
    }
}
