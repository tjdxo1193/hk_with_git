package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.domain.eai.TrsStateful;
import lims.api.integration.enums.DMRItemDiv;
import lims.api.integration.enums.FinalOrderStatus;
import lims.api.integration.enums.MaterialCharCode;
import lims.api.integration.enums.SAPPItemType;
import lims.api.ts.enums.TestJudgement;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;

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
        private String werks;
        private String dispo;
        private String matnr;
        private DMRItemDiv itemDiv;

        private CosmeticMaterial cosmeticFinished;
        private CosmeticBulkMaterial cosmeticBulk;
        private DrugMaterial quasiDrugFinished;
        private DrugBulkMaterial quasiDrugBulk;
    }

    @Getter
    @Builder
    @EqualsAndHashCode(callSuper = false)
    public static class MaterialAll extends TrsStateful {
        private List<CosmeticMaterial> cosmetics;
        private List<CosmeticBulkMaterial> cosmeticBulks;
        private List<DrugMaterial> drugs;
        private List<DrugBulkMaterial> drugBulks;
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class CosmeticMaterial {
        @Setter
        private Integer materialIdx;
        @Setter
        private Integer idx;
        private String pitmCd;      // 자재코드
        private String pitmNm;      // 제품명
        private String perPitmNm;   // 허가 제품명
        private String pkgUnit;     // 포장 단위
        private String mstFmlNo;    // 제품표준서 번호
        private String vdrBpCd;     // 거래처 BP 코드
        private String vdrNm;       // 거래처명
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class CosmeticBulkMaterial {
        @Setter
        private Integer materialIdx;
        @Setter
        private Integer idx;
        private String pitmCd;      // 자재코드
        private String pitmNm;      // 제명 명
        private String perPitmNm;   // 허가 제품명
        private String vdrBpCd;     // 거래처 BP 코드
        private String vdrNm;       // 거래처명
        private String pitmDiv;     // 품목 구분
        private String typDiv;      // 유형 분류
        private String typDtl;      // 유형 세부 분류
        private String chatDiv;     // 특성 구분
        private String ftnDiv;      // 기능성 구분
        private String useLmt;      // 사용기간
        private String openLmt;     // 개봉 후 사용기간
        private String labNo;       // 처방코드
        private String fmlNo;       // 표준서 번호
        private String pkgUnit;     // 포장 단위R
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class DrugMaterial {
        @Setter
        private Integer materialIdx;
        @Setter
        private Integer idx;
        private String pitmCd;      // 자재코드
        private String pitmNm;      // 제품명
        private String perPitmNm;   // 허가 제품명
        private String pkgUnit;     // 포장단위
        private String mstFmlNo;    // 제품표준서 번호
        private String vdrBpCd;     // 거래처 BP 코드
        private String vdrNm;       // 거래처명
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class DrugBulkMaterial {
        @Setter
        private Integer materialIdx;
        @Setter
        private Integer idx;
        private String pitmCd;      // 자재코드
        private String pitmNm;      // 제품명
        private String wrtDt;       // 작성일자
        private String wrtId;       // 작성자 ID
        private String labNo;       // 처방 코드
        private String mstFmlNo;    // 제품표준서 번호
        private String pitmDiv;     // 제뭉 구분
        private String enmDt;       // 제정 일자
        private String perDt;       // 허가 일자
        private String enfoDt;      // 시행 일자
        private String perNo;       // 허가 번호
        private String fmln;        // 제형 및 성상
        private String divNo;       // 분류 번호
        private String pkgUnit;     // 포장 단위
        private String useTrm;      // 사용 기간
        private String slvAmtd;     // 기준 및 시험방법
    }

}