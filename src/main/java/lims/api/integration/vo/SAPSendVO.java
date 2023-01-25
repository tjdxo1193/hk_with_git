package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.domain.eai.TrsStateful;
import lims.api.integration.enums.CompanyType;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.TestResultType;
import lims.api.integration.enums.TestStatusProcess;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SAPSendVO {

    /**
     * 품질검사 결과판정
     */
    @Getter
    @Builder
    public static class TestResult extends TrsStateful {
        @Setter(AccessLevel.NONE)
        private final InterfaceSystemType targetSystem = InterfaceSystemType.SAP;
        private String zqcreqno;
        private String zqcno;
        @EnumType(TestStatusProcess.class)
        private Integer status;
        private TestResultType sytJdg;
        private String specGrv;
        private String rmk;
        private String nonCfmReason;
        private String menge;
    }

    /**
     * 품질검사 상태
     */
    @Getter
    @Builder
    public static class TestStatus extends TrsStateful {
        @Setter(AccessLevel.NONE)
        private final InterfaceSystemType targetSystem = InterfaceSystemType.SAP;
        private String reqno;
        @EnumType(TestStatusProcess.class)
        private Integer status;
        private String holdReason;
        private String zexfield2;
        private String zexfield3;
        private String zexfield4;
        private String zexfield5;
    }

    /**
     * 품질검사 횟수(구매오더)
     */
    @Getter
    @Setter
    public static class TestPerformanceOfPurchaseInbound extends TrsStateful {
        @Setter(AccessLevel.NONE)
        private String kokrs = CompanyType.KOLMAR.getValue();
        private String gjahr;
        private String poper;
        private String werks;
        private String matnr;
        private String ebeln;
        private String zpoQty;
        private String zqcQty;
    }

    /**
     * 품질검사 횟수 (생산입고)
     */
    @Getter
    @Setter
    public static class TestPerformanceOfManufactureInbound extends TrsStateful {
        private String guid;
        private Integer seq;
        private String matnr;
        private String werks;
        private String charg;
        private String zqidate;
        private String zqicount;
        private String zcancel;
    }

    /**
     * 유무형 자산 이동 내역
     */
    @Getter
    @Setter
    public static class AssetsMovementHistory extends TrsStateful {
        @Setter(AccessLevel.NONE)
        private String bukrs = CompanyType.KOLMAR.getValue();
        private String anlnFrom;
        private String bzdat;
        private String zzpicFrom;
        private String zzpicTo;
        private String kostlFrom;
        private String kostlTo;
    }

}