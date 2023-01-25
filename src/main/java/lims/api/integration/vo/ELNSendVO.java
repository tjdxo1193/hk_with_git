package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.enums.ELNCmdType;
import lims.api.integration.domain.eai.TrsStateful;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.TestStatusProcess;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class ELNSendVO {

    /**
     * 시험항목별 시험방법
     */
    @Getter
    @Setter
    public static class TestMethodByItem extends TrsStateful {
        private Integer idx;
        private Integer degree;
        private String amitmCd;
        private String aitmCd;
        private String aitmKn;
        private String aitmEn;
        private String aitmAbbr;
        private String vriaNo;
        private String vriaKn;
        private String vriaEn;
        private String useYn;
        private String rmk;
        private String udtDate;
        private String udtTime;
        private ELNCmdType cmdType;
    }

    /**
     * 품질검사 상태
     */
    @Getter
    @Setter
    public static class TestStatus extends TrsStateful {
        @Setter(AccessLevel.NONE)
        private final InterfaceSystemType targetSystem = InterfaceSystemType.ELN;
        private String pdtOrderNo;
        private String lotNo;
        private String batchNo;
        @EnumType(TestStatusProcess.class)
        private String status;
        private String holdReason;
    }

}