package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.domain.eai.TrsStateful;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.ReportDivOfNonCfm;
import lims.api.integration.enums.TestResultType;
import lims.api.integration.enums.TestStatusProcess;
import lombok.*;

public class SRMSendVO {

    /**
     * 품질검사 상태
     */
    @Getter
    @Builder
    public static class TestStatus extends TrsStateful {
        @Setter(AccessLevel.NONE)
        private final InterfaceSystemType targetSystem = InterfaceSystemType.SRM;
        private String ifId;
        private String phsOrderNo;
        private String lotNo;
        private String batchNo;
        @EnumType(TestStatusProcess.class)
        private Integer status;
        private String holdReason;
    }

    /**
     * 품질검사 결과 판정
     */
    @Getter
    @Builder
    public static class TestResult extends TrsStateful {
        @Setter(AccessLevel.NONE)
        private final InterfaceSystemType targetSystem = InterfaceSystemType.SRM;
        private String ifId;
        private String phsOrderNo;
        private String lotNo;
        private String batchNo;
        private TestResultType sytJdg;
        private String specGrv;
        private String hardness;
        private String rmk;
        private String nonCfmReason;
    }

    /**
     * 부적합 통보서
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NonCfmReport extends TrsStateful {
        private String batchNo;
        private String ispReqNo;
        private String ansNo;
        private String phsOrderNo;
        private String lotNo;
        private String nonCfmReason;
        private String emId;
        private ReportDivOfNonCfm reportDiv;
        private String fileName;
        private byte[] fileData;
    }

}