package lims.api.integration.vo.intergation;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.enums.ReportDivOfNonCfm;
import lims.api.integration.enums.TestResultType;
import lims.api.integration.enums.TestStatusProcess;
import lims.api.integration.vo.MESSendVO;
import lims.api.integration.vo.SAPSendVO;
import lims.api.integration.vo.SRMSendVO;
import lims.api.util.StringUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class InterfaceSendVO {

    @Getter
    @Builder
    public static class TestStatus {
        private String lotNo;               // 제조번호
        private String batchNo;             // 배치번호
        private String holdReason;          // 보류사유
        @EnumType(TestStatusProcess.class)
        private Integer status;
        private String ispReqNo;            // 품질검사요청번호
        private String phsOrderNo;          // 구매오더번호
        private String pdtOrderNo;          // 생산오더번호

        public SAPSendVO.TestStatus toSAP() {
            return SAPSendVO.TestStatus.builder()
                    .reqno(ispReqNo)
                    .status(status)
                    .holdReason(holdReason)
                    .build();
        }

        public SRMSendVO.TestStatus toSRM() {
            return SRMSendVO.TestStatus.builder()
                    .ifId(StringUtil.generateUUID(22))
                    .phsOrderNo(phsOrderNo)
                    .lotNo(lotNo)
                    .batchNo(batchNo)
                    .status(status)
                    .holdReason(holdReason)
                    .build();
        }

        public MESSendVO.TestStatus toMES() {
            return MESSendVO.TestStatus.builder()
                    .pdtOrderNo(pdtOrderNo)
                    .lotNo(lotNo)
                    .batchNo(batchNo)
                    .status(status)
                    .holdReason(holdReason)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class TestResult {
        @Setter(AccessLevel.NONE)
        private final Integer status = TestStatusProcess.TEST_COMPLETE.getValue();
        private TestResultType sytJdg;
        private String lotNo;
        private String batchNo;
        private String specGrv;             // 비중 (반제품 벌크가 아니면 null)
        private String hardness;            // 경도 (반제품 벌크가 아니면 null)
        private String rmk;                 // 비고
        private String nonCfmReason;        // 부적합 사유
        private String ispReqNo;            // 품질검사요청번호
        private String ansNo;               // 품질검사번호
        private String menge;               // 검체량(총검체량 : 시험+기타+관리폼+추가)
        private String phsOrderNo;          // 구매오더번호
        private String pdtOrderNo;          // 생산오더번호

        public SAPSendVO.TestResult toSAP() {
            return SAPSendVO.TestResult.builder()
                    .zqcreqno(ispReqNo)
                    .zqcno(ansNo)
                    .menge(menge)
                    .status(status)
                    .sytJdg(sytJdg)
                    .specGrv(specGrv)
                    .rmk(rmk)
                    .nonCfmReason(nonCfmReason)
                    .build();
        }

        public SRMSendVO.TestResult toSRM() {
            return SRMSendVO.TestResult.builder()
                    .ifId(StringUtil.generateUUID(22))
                    .phsOrderNo(phsOrderNo)
                    .lotNo(lotNo)
                    .batchNo(batchNo)
                    .sytJdg(sytJdg)
                    .specGrv(specGrv)
                    .hardness(hardness)
                    .rmk(rmk)
                    .nonCfmReason(nonCfmReason)
                    .build();
        }

        public MESSendVO.TestResult toMES() {
            return MESSendVO.TestResult.builder()
                    .pdtOrderNo(pdtOrderNo)
                    .lotNo(lotNo)
                    .batchNo(batchNo)
                    .sytJdg(sytJdg)
                    .specGrv(specGrv)
                    .hardness(hardness)
                    .rmk(rmk)
                    .nonCfmReason(nonCfmReason)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class NonconformityReport {
        private String batchNo;
        private String ispReqNo;
        private String ansNo;
        private String lotNo;
        private String nonCfmReason;
        private String emId;
        private String phsOrderNo;
        private String pdtOrderNo;
        private String orderItm;

        public SRMSendVO.NonCfmReport toSRM() {
            return SRMSendVO.NonCfmReport.builder()
                    .batchNo(batchNo)
                    .ispReqNo(ispReqNo)
                    .ansNo(ansNo)
                    .phsOrderNo(phsOrderNo)
                    .lotNo(lotNo)
                    .nonCfmReason(nonCfmReason)
                    .emId(emId)
                    .build();
        }

        public MESSendVO.NonCfmReport toMES() {
            return MESSendVO.NonCfmReport.builder()
                    .batchNo(batchNo)
                    .ispReqNo(ispReqNo)
                    .ansNo(ansNo)
                    .pdtOrderNo(pdtOrderNo)
                    .orderItm(orderItm)
                    .lotNo(lotNo)
                    .nonCfmReason(nonCfmReason)
                    .emId(emId)
                    .build();
        }
    }

}