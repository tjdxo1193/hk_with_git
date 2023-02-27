package lims.api.ts.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.FileService;
import lims.api.common.service.UserService;
import lims.api.integration.enums.TestResultType;
import lims.api.integration.enums.TestStatusProcess;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.ts.dao.TestResultApprDao;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lims.api.ts.service.TestResultApprService;
import lims.api.ts.vo.TestResultApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestResultApprServiceImpl implements TestResultApprService {

    private final TestResultApprDao dao;
    private final ApproveService approveService;
    private final UserService userService;
    private final IntegrationSender sender;

    @Override
    public List<TestResultApprVO> findAll(TestResultApprVO param) {
        param.setAnsProcCd(TestProcess.TEST_APPROVED.getProcessCode());
        param.setWithDelegateUserIds(userService.getDelegateAssignUserIdsWithMe(param.getAprUid()));
        return dao.findAll(param);
    }

    @Override
    public List<TestResultApprVO> findResultItem(Integer ansIdx) {
        TestResultApprVO param = new TestResultApprVO();
        param.setAnsIdx(ansIdx);
        param.setAnsProcCd(TestProcess.TEST_APPROVED.getProcessCode());
        param.setRstProcCd(TestProcess.TEST_RESULT_INPUT_COMPLETED.getProcessCode());
        return dao.findResultItem(param);
    }

    @Override
    public void approve(List<TestResultApprVO> list) {
        int result = 0;
        for(TestResultApprVO row : list) {
            /**
             * TODO 결과승인할때 품목유형이 완제품일 경우에는 QMS I/F 필요 (재고량?)
             * String completedProductSet = 'S0180100'; // enum 클래스로 빼기
             * String completedProductSingle = 'S0180101'; // enum 클래스로 빼기
             * if(row.getPitmTyp.equals(completedProductSet) || row.getPitmTyp.equals(completedProductSingle)) {
             *  //QMS 인터페이스 발신 로직 구현 필요
             * }
             */
            approveService.approve(row.getRstAprReqIdx());

            row.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
            result += dao.approve(row);

            TestResultType sytJdg = null;
            if(row.getSytJdg().equals("S0110001")){
                sytJdg = TestResultType.C;
            }else if(row.getSytJdg().equals("S0110002")){
                sytJdg = TestResultType.N;
            }else if(row.getSytJdg().equals("S0110003")){
                sytJdg = TestResultType.P;
            }

            //시험결과 데이터 보내기
            InterfaceSendVO.TestResult data = InterfaceSendVO.TestResult.builder()
                    .sytJdg(sytJdg)
                    .lotNo(row.getLotNo())
                    .splLotNo(row.getSplLotNo())
                    .batchNo(row.getBatchNo())
                    .rmk(row.getPitmSpcc())
                    .nonCfmReason(row.getNonCfmRea())
                    .ispReqNo(row.getIspReqNo())
                    .ansNo(row.getAnsNo())
                    .menge(row.getSmpVolTot())
                    .phsOrderNo(row.getPhsOrderNo())
                    .pdtOrderNo(row.getPdtOrderNo())
                    .testType(TestType.of(row.getAnsTyp()))
                    .build();
            sender.sendTestResult(data);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestHold(List<TestResultApprVO> list) {
        int result = 0;
        for(TestResultApprVO row : list) {
            result += dao.requestHold(row);

            // 보류 시 진행상태 전송
            InterfaceSendVO.TestStatus data = InterfaceSendVO.TestStatus.builder()
                    .lotNo(row.getLotNo())
                    .splLotNo(row.getSplLotNo())
                    .batchNo(row.getBatchNo())
                    .holdReason(row.getHldRea())
                    .status(TestStatusProcess.TEST_HOLD.getValue())
                    .ispReqNo(row.getIspReqNo())
                    .phsOrderNo(row.getPhsOrderNo())
                    .pdtOrderNo(row.getPdtOrderNo())
                    .testType(TestType.of(row.getAnsTyp()))
                    .build();
            sender.sendTestStatus(data);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestRejection(List<TestResultApprVO> list) {
        int result = 0;
        for(TestResultApprVO row : list) {
            row.setAnsProcCd(TestProcess.TEST_APPROVAL_REJECT.getProcessCode());
            result += dao.requestRejection(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}