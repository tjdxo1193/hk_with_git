package lims.api.sc.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectProductionPerformanceVO {
    /* QT_PITM_ANS_REQ (품목 시험 의뢰) */
    private String ispReqNo;        // 검사 요청 번호
    private String ispReqDt;        // 검사 요청 일자
    private String mtrCd;           // 자재 코드
    private String mtrNm;           // 자재 명
    private String batchNo;         // 배치 번호
    private String etrNo;           // 입고 수량
    private String inpUnit;         // 입력 단위
    private String savePla;         // 저장 위치
    private String etrDt;           // 입고 일자
    private String phsOrderTyp;     // 구매 오더 유형
    private String phsOrderNo;      // 구매 오더 번호
    private String phsOrderItm;     // 구매 오더 항목
    private String itmCtg;          // 아이템 카테고리
    private String phsUnitPre;      // 구매 단가
    private String amtUnit;         // 가격 단위
    private String currCd;          // 통화키
    private String amtLoccurr;      // 금액 현지 통화
    private String phsOrderQty;     // 구매 오더 수량
    private String phsOrderUnit;    // 구매 오더 단위
    private String splCd;           // 공급사 코드
    private String splNm;           // 공급사 명
    private String phsCrgNm;        // 구매 담당자
    private String dlvYn;           // 택배 유무
    private String vdrCtrtDt;       // 납품 약정 일자
    private String vdrRsvTm;        // 납품 예약 시간
    private String vdrRptRcpCrst;   // 거래처 성적서 접수 현황
    private String lotNo;           // 제조 번호
    private String repBomNo;        // 대체 BOM 번호
    private String splLotNo;        // 공급사 제조 번호
    private String makDt;           // 제조 일자
    private String strgLmt;         // 보관 기한
    private String useLmt;          // 사용 기한
    private String pdtOrderTyp;     // 생산 오더 유형
    private String pdtOrderNo;      // 생산 오더 번호
    private String itnPrsCompCd;    // 내부 임가공 업체 코드
    private String otmPrsCompNm;    // 내부 임가공 업체 명
    private String makEqp;          // 제조 설비
    private String wrkNm;           // 작업자 명
    private String mtrDocNo;        // 자재 문서 번호
    private String mtrDocYr;        // 자재 문서 연도
    private String mtrDocItm;       // 자재 문서 항목
    private String csmBpCd;         // 고객처 BP 코드
    private String csmNm;           // 고객처 명
    private String addCol1;         // 입고 유형 (A: 구매, B: 생산, C: 수동 의뢰)
    private String addCol2;         // 입고 취소 여부 (취소: X)
    private String addCol3;         // 추가 필드 3
    private String addCol4;         // 추가 필드 4
    private String revDs;           // 수신 일지

    /* QT_PITM_ANS_PROC (품목 시험 프로세스)*/
    private String plntCd;          // 사업장 코드
    private Integer ansIdx;         // 시험 IDX
    private Integer reqIdx;         // 의뢰 IDX
    private String pitmSpecIdx;     // 품목 규격서 IDX
    private String ansProcCd;       // S013 진행상태 코드
    private String sytJdg;          // S011 종합 판정
    private String reqDt;           // 의뢰 일자
    private String ansEdt;          // 시험 예정 일자
    private String ansNo;           // 시험 번호
    private String rcpDt;           // 접수 일자
    private String rcpDptCd;        // 접수 부서 코드
    private String rcpUid;          // 접수 UID
    private String rcpRmk;          // 접수 비고
    private String clltDt;          // 채취 일자
    private String clltUid;         // 채취 UID
    private String clltRmk;         // 채취 비고
    private String assNo;           // 지시 번호
    private String assDt;           // 지시 일자
    private String assSpcc;         // 지시 특이사항
    private Integer assAprReqIdx;   // 지시 승인 요청 IDX
    private String cplRqmDt;        // 완료 요구 일자
    private String wrkSttCd;        // U009 완료 상태 코드
    private String revwDt;          // 검토 일자
    private String revwUid;         // 검토 UID
    private String revwCmmt;        // 검토 의견
    private Integer rstAprReqIdx;   // 결과 승인 요청 IDX
    private String rjtUid;          // 반려 UID
    private String rjtDs;           // 반려 일시
    private String rjtRea;          // 반려 사유
    private String aprRea;          // 승인 사유
    private String hldYn;           // 보류 여부
    private String hldDs;           // 보류 일시
    private String hldUid;          // 보류 UID
    private String hldRea;          // 보류 사유
    private String hldCanlDs;       // 보류 취소 일시
    private String hldCanlUid;      // 보류 취소 UID
    private String hldCanlRea;      // 보류 취소 사유
    private String ansCanlDs;       // 시험 취소 일시
    private String ansCanlUid;      // 시험 취소 UID
    private String ansCanlRea;      // 시험 취소 사유
    private String oosYn;           // OOS 여부
    private String oosReqUid;       // OOS 의뢰 UID
    private String oosReqDs;        // OOS 의뢰 일시
    private String oosCd;           // OOS 코드

    /* 별칭 */
    private Integer ansCnt;         // 검사횟수
    private Character ftnYn;        // 기능성유무
    private Character ifYn;         // 연계여부
    private String ifDs;            // 연계일시
}
