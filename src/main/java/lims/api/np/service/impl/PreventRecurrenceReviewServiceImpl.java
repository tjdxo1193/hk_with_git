package lims.api.np.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.np.dao.PreventRecurrenceReviewDao;
import lims.api.np.enums.NonCfmProcess;
import lims.api.np.service.PreventRecurrenceReviewService;
import lims.api.np.vo.PreventRecurrenceReviewVO;
import lims.api.ts.enums.TestJudgement;
import lims.api.ts.enums.TestProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreventRecurrenceReviewServiceImpl implements PreventRecurrenceReviewService {

    private final PreventRecurrenceReviewDao dao;
    private final ApproveService approveService;

    @Override
    public List<PreventRecurrenceReviewVO> findAll(PreventRecurrenceReviewVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        param.setSytJdg(TestJudgement.UNSUITABLE.getJudgementCode());
        List<String> processList = new ArrayList<>();
        processList.add(0, NonCfmProcess.NON_CFM_REPORT_APPROVE.getProcessCode());
        processList.add(1, NonCfmProcess.PRV_RCR_REPORT_REJECT.getProcessCode());
        param.setProcessList(processList);
        return dao.findAll(param);
    }

    @Override
    public List<PreventRecurrenceReviewVO> findResultItem(PreventRecurrenceReviewVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return dao.findResultItem(param);
    }

    @Override
    public void save(PreventRecurrenceReviewVO param) {
        int result = dao.save(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }

    }

    @Override
    public void request(PreventRecurrenceReviewVO param) {
        int reoPrevAprReqIdx = approveService.requestApprove(param.getApproveInfo());
        param.setReoPrevAprReqIdx(reoPrevAprReqIdx);
        param.setNonCfmProcCd(NonCfmProcess.PRV_RCR_REPORT_REQUEST.getProcessCode());
        int result = dao.request(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
