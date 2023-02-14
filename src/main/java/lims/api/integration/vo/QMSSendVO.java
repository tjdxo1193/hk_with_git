package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.domain.eai.TrsStateful;
import lims.api.integration.enums.SAPPItemType;
import lims.api.integration.enums.FinalOrderStatus;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class QMSSendVO {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class ShiptReq extends TrsStateful {
        private String plntCd;
        private Integer reqIdx;
        private String lotNo;
        private String splLotNo;
        private String phsOrderTyp;
        private String phsOrderNo;
        private String phsOrderItm;
        private String pdtOrderTyp;
        private String pdtOrderNo;

        private List<ShiptTest> tests = new ArrayList<>();
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class ShiptTest {
        private String plntCd;
        private Integer reqIdx;
        private String ispReqNo;
        private String batchNo;
        private String mtrCd;
        private String mtrNm;
        private String csmNm;
        private String ansNo;

        @EnumType(TestType.class)
        transient private String ansTyp;
        @EnumType(TestProcess.class)
        transient private String ansProcCd;
        @EnumType(FinalOrderStatus.class)
        transient private Integer finlStt;
        transient private SAPPItemType mtart;

        private List<ShiptPerform> performs = new ArrayList<>();

        transient private Integer shiptIdx;
        transient private Integer seq;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class ShiptPerform {
        private String mtrCd;
        private String mtrNm;
        private String phsOrderNo;
        private String phsOrderItm;
        private String pdtOrderNo;
        private String batchNo;
        private String lotNo;
        private Integer reqIdx;
        private String ispReqNo;
        private String splLotNo;
        private String phsOrderTyp;
        private String pdtOrderTyp;
        private String ansNo;

        transient private Integer shiptTestIdx;
        transient private Integer seq;
    }

    @Getter
    @Setter
    public static class ShiptValidate {
        private String plntCd;
        private Integer reqIdx;
        private String batchNo;
        private String lotNo;
        private String phsOrderNo;
        private String phsOrderItm;
        private Integer finlStt;
    }

}