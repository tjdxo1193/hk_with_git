package lims.api.integration.vo.intergation;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.enums.*;
import lims.api.integration.vo.MESSendVO;
import lims.api.integration.vo.SAPSendVO;
import lims.api.integration.vo.SRMSendVO;
import lims.api.ts.enums.TestType;
import lims.api.util.StringUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @implNote
 *  타 시스템으로 인터페이스 데이터를 전송하는 메서드의 파라미터로 사용되는 객체입니다.
 *
 *  각 파라미터 클래스의 인스턴스를 생성한 뒤 인스턴스 필드에 데이터를 채워서 넘겨주시면 됩니다.
 *  인스턴스를 필드를 채우는 방식은 생성자 or Builder 패턴으로 구성합니다.
 *
 *  인스턴스 필드 외 기타 메서드는 인터페이스 관련 코드 내에서 사용되는 유틸 메서드이니 파라미터 생성과 관련이 없습니다.
 */
public class InterfaceSendVO {

    /**
     * SRM에서 오는 의뢰는 구매 입고 건이기 때문에
     * 다른 시스템과 다르게 제조번호 뿐만 아니라 '공급처 제조번호'라는 것이 있습니다.
     *
     * 따라서 SRM 관련 제조번호를 인터페이스 송신할 때,
     * '공급처 제저번호'가 있다면 '공급처 제조번호'를, 없다면 일반 '제조 번호'를 송신합니다.
     */
    private static String getSRMLotNo(String splLotNo, String lotNo) {
        return StringUtil.getOrDefault(splLotNo, lotNo);
    }

    @Builder
    public static class TestStatus extends InterfaceSystemDistinguishable {
        private String lotNo;               // 제조번호
        private String splLotNo;            // 공급사 제조번호
        private String batchNo;             // 배치번호
        private String holdReason;          // 보류사유
        @EnumType(TestStatusProcess.class)
        private Integer status;
        private String ispReqNo;            // 품질검사요청번호
        @Getter
        private String phsOrderNo;          // 구매오더번호
        @Getter
        private String pdtOrderNo;          // 생산오더번호
        @Getter
        private TestType testType;          // 시험 유형

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
                    .lotNo(getSRMLotNo(splLotNo, lotNo))
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

    @Builder
    public static class TestResult extends InterfaceSystemDistinguishable {
        @Setter(AccessLevel.NONE)
        private final Integer status = TestStatusProcess.TEST_COMPLETE.getValue();
        private TestResultType sytJdg;
        private String lotNo;
        private String splLotNo;            // 공급사 제조번호
        private String batchNo;
        private String specGrv;             // 비중 (반제품 벌크가 아니면 null)
        private String hardness;            // 경도 (반제품 벌크가 아니면 null)
        private String rmk;                 // 비고
        private String nonCfmReason;        // 부적합 사유
        private String ispReqNo;            // 품질검사요청번호
        private String ansNo;               // 품질검사번호
        private String menge;               // 검체량(총검체량 : 시험+기타+관리폼+추가)
        @Getter
        private String phsOrderNo;          // 구매오더번호
        @Getter
        private String pdtOrderNo;          // 생산오더번호
        @Getter
        private TestType testType;          // 시험 유형

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
                    .lotNo(getSRMLotNo(splLotNo, lotNo))
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

    @Builder
    public static class NonconformityReport extends InterfaceSystemDistinguishable {
        private String batchNo;
        private String ispReqNo;
        private String ansNo;
        private String lotNo;
        private String splLotNo;            // 공급사 제조번호
        private String nonCfmReason;
        private String emId;
        @Getter
        private String phsOrderNo;
        @Getter
        private String pdtOrderNo;
        private String orderItm;
        @Getter
        private TestType testType;          // 시험 유형

        public SRMSendVO.NonCfmReport toSRM(ReportDivOfNonCfm reportDiv) {
            return SRMSendVO.NonCfmReport.builder()
                    .batchNo(batchNo)
                    .ispReqNo(ispReqNo)
                    .ansNo(ansNo)
                    .phsOrderNo(phsOrderNo)
                    .lotNo(getSRMLotNo(splLotNo, lotNo))
                    .nonCfmReason(nonCfmReason)
                    .emId(emId)
                    .reportDiv(reportDiv)
                    .build();
        }

        public MESSendVO.NonCfmReport toMES(ReportDivOfNonCfm reportDiv) {
            return MESSendVO.NonCfmReport.builder()
                    .batchNo(batchNo)
                    .ispReqNo(ispReqNo)
                    .ansNo(ansNo)
                    .pdtOrderNo(pdtOrderNo)
                    .orderItm(orderItm)
                    .lotNo(lotNo)
                    .nonCfmReason(nonCfmReason)
                    .emId(emId)
                    .reportDiv(reportDiv)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class MethodByItem {
        private String amitmCd;
        private ELNCmdType cmdType;
    }

    @Builder
    public static class PurchaseInboundTestPerformance {
        private String plntCd;      // 공장코드
        private String pitmCd;      // 품목코드
        private String year;        // 회계연도
        private String month;       // 기간(월)
        private String phsOrderNo;  // 구매오더번호
        private Integer inputQty;      // 입고량
        private String testCnt;       // 검사횟수

        public SAPSendVO.TestPerformanceOfPurchaseInbound toSAP() {
            SAPSendVO.TestPerformanceOfPurchaseInbound result = new SAPSendVO.TestPerformanceOfPurchaseInbound();
            result.setGjahr(year);
            result.setPoper(month);
            result.setWerks(plntCd);
            result.setMatnr(pitmCd);
            result.setEbeln(phsOrderNo);
            result.setZpoQty(String.valueOf(inputQty));
            result.setZqcQty(testCnt);
            return result;
        }
    }

    @Builder
    public static class ManufactureInboundTestPerformance {
        private String pitmCd;      // 품목코드
        private String pdtOrderNo;  // 생산오더번호
        private String batchNo;     // 배치번호
        private String testReqDt;   // 품질검사 의뢰일자. 8자리 (YYYYMMDD)
        private String testCnt;     // 검사횟수

        public SAPSendVO.TestPerformanceOfManufactureInbound toSAP() {
            SAPSendVO.TestPerformanceOfManufactureInbound result = new SAPSendVO.TestPerformanceOfManufactureInbound();
            result.setMatnr(pitmCd);
            result.setWerks(pdtOrderNo);
            result.setCharg(batchNo);
            result.setZqidate(testReqDt);
            result.setZqicount(testCnt);
            result.setZcancel(TestPerformanceCancel.N);
            return result;
        }
    }
}