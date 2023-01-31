package lims.api.np.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.ts.enums.TestJudgement;
import lims.api.ts.enums.TestProcess;
import lims.api.np.dao.NonconformityReportWrtDao;
import lims.api.np.enums.NonCfmProcess;
import lims.api.np.service.NonconformityReportWrtService;
import lims.api.np.vo.NonconformityReportWrtVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NonconformityReportWrtServiceImpl implements NonconformityReportWrtService {

    private final NonconformityReportWrtDao dao;
    private final ApproveService approveService;

    @Override
    public List<NonconformityReportWrtVO> findAll(NonconformityReportWrtVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        param.setSytJdg(TestJudgement.UNSUITABLE.getJudgementCode());
        List<String> processList = new ArrayList<>();
        processList.add(0, NonCfmProcess.NON_CFM_REPORT_WRITE.getProcessCode());
        processList.add(1, NonCfmProcess.NON_CFM_REPORT_REJECT.getProcessCode());
        param.setProcessList(processList);
        return dao.findAll(param);
    }

    @Override
    public List<NonconformityReportWrtVO> findResultItem(NonconformityReportWrtVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return dao.findResultItem(param);
    }

    @Override
    public void save(NonconformityReportWrtVO param) {
        param.setNonCfmProcCd(NonCfmProcess.NON_CFM_REPORT_WRITE.getProcessCode());
        int result = dao.save(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void request(NonconformityReportWrtVO param) {
        int nonCfmAprReqIdx = approveService.requestApprove(param.getApproveInfo());
        param.setNonCfmAprReqIdx(nonCfmAprReqIdx);
        param.setNonCfmProcCd(NonCfmProcess.NON_CFM_REPORT_REQUEST.getProcessCode());
        dao.save(param);
        int result = dao.requestAppr(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
