package lims.api.np.service.impl;

import lims.api.auth.domain.Token;
import lims.api.auth.service.TokenHttpResolver;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.integration.enums.ReportDivOfNonCfm;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.ConvertMrd;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.integration.vo.intergation.TargetFile;
import lims.api.ts.enums.TestJudgement;
import lims.api.ts.enums.TestProcess;
import lims.api.np.dao.NonconformityReportApprDao;
import lims.api.np.enums.NonCfmProcess;
import lims.api.np.service.NonconformityReportApprService;
import lims.api.np.vo.NonconformityReportApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NonconformityReportApprServiceImpl implements NonconformityReportApprService {

    private final NonconformityReportApprDao dao;
    private final ApproveService approveService;
    private final IntegrationSender sender;
    private final TokenHttpResolver tokenHttpResolver;
    private final JwtResolver jwtResolver;

    @Override
    public List<NonconformityReportApprVO> findAll(NonconformityReportApprVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        param.setSytJdg(TestJudgement.UNSUITABLE.getJudgementCode());
        param.setNonCfmProcCd(NonCfmProcess.NON_CFM_REPORT_REQUEST.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public List<NonconformityReportApprVO> findResultItem(NonconformityReportApprVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return dao.findResultItem(param);
    }

    @Override
    public void approve(NonconformityReportApprVO param) {
        Token token = tokenHttpResolver.getAccessToken();
        String jwt = token.getJwt();
        param.setNonCfmProcCd(NonCfmProcess.NON_CFM_REPORT_APPROVE.getProcessCode());
        approveService.approve(param.getNonCfmAprReqIdx());
        int result = dao.approve(param);

        //부적합통보서 보내기
        InterfaceSendVO.NonconformityReport data = InterfaceSendVO.NonconformityReport.builder()
                .batchNo(param.getBatchNo())
                .ispReqNo(param.getIspReqNo())
                .ansNo(param.getAnsNo())
                .lotNo(param.getLotNo())
                .nonCfmReason(param.getNonCfmRea())
                .emId(jwtResolver.getUserId(jwt))
                .phsOrderNo(param.getPhsOrderNo())
                .pdtOrderNo(param.getPdtOrderNo())
                .orderItm(param.getPhsOrderItm())
                .build();
        //IFData, 부적합통보서, 재발방지대책서
        sender.sendNonconformityReport(data,
            new ConvertMrd("NONCONFORMITY_REPORT.mrd", "/rp ["+param.getPlntCd()+"] ["+param.getAnsIdx()+"]"
                    , new TargetFile("부적합통보서_"+param.getBatchNo()+".pdf")),
            new ConvertMrd("MEASURES_TO_PREVENT_RECURRENCE.mrd", "/rp ["+param.getPlntCd()+"] ["+param.getAnsIdx()+"]"
                    , new TargetFile("재발방지대책서_"+param.getBatchNo()+".doc"))
        );
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(NonconformityReportApprVO param) {
        param.setNonCfmProcCd(NonCfmProcess.NON_CFM_REPORT_REJECT.getProcessCode());
        int result = dao.reject(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }

    }
}
