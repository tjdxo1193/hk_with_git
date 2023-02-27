package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.domain.eai.TrsStateful;
import lims.api.integration.enums.DMRItemDiv;
import lims.api.integration.enums.FinalOrderStatus;
import lims.api.integration.enums.SAPPItemType;
import lims.api.ts.enums.TestJudgement;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
        private String mtrTyp;
        private String mtrMrp;
        @EnumType(TestJudgement.class)
        private String shiptJdgDiv;
        private String etrQty;

        private List<ShiptTest> tests = new ArrayList<>();

        public String getTestCancelFlag() {
            return "X";
        }

        public String getTestCancelProcessCode() {
            return TestProcess.TEST_CANCEL.getProcessCode();
        }
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
        @EnumType(TestJudgement.class)
        private String pitmJdgDiv;
        private String smpQty;

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

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class Material {
        private final String werks;
        private final String matnr;
        private final String dispo;
        private final DMRItemDiv itemDiv;
    }

    @Getter
    @Builder
    public static class MaterialAll {
        private List<CosmeticFinishedMaterial> cosmeticFinisheds;
        private List<CosmeticBulkMaterial> cosmeticBulks;
        private List<QuasiDrugFinishedMaterial> drugFinisheds;
        private List<QuasiDrugBulkMaterial> drugBulks;
    }

    @Getter
    @Builder
    public static class CosmeticFinishedMaterial {

    }

    @Getter
    @Builder
    public static class CosmeticBulkMaterial {

    }

    @Getter
    @Builder
    public static class QuasiDrugFinishedMaterial {

    }

    @Getter
    @Builder
    public static class QuasiDrugBulkMaterial {

    }

}