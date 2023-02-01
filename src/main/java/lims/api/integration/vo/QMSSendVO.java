package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.enums.SAPPItemType;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class QMSSendVO {

    @Getter
    @Builder
    public static class ShiptBase {
        private String lotNo;
        private String pdtOrderNo;
        private String phsOrderNo;
        private String phsOrderItm;
    }

    @Getter
    @Setter
    public static class ShiptTest {
        private String lotNo;
        private String pdtOrderNo;
        private String phsOrderNo;
        private String phsOrderItm;
        @EnumType(TestType.class)
        private String ansTyp;
        @EnumType(TestProcess.class)
        private String ansProcCd;

        private SAPPItemType mtart;
    }

}