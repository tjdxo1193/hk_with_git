package lims.api.sc.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchForMonitorTestVO {
    /* MT_MITM_ANS_PROC (MT_모니터링 항목 시험 프로세스) */
    private Integer mitmSpecIdx;    // 모니터링 항목 규격서 IDX
    private String ansProcCd;       // S013 시험 진행 상태 코드
    private String sytJdg;          // S011 종합 판정
    private String reqNo;           // 의뢰 번호
    private String reqDt;           // 의뢰 일자
    private String ansEdt;          // 시험 예정 일자
    private String reqDs;           // 의뢰 일시
    private String reqDptCd;        // 의뢰 부서 코드
    private String reqUid;          // 의뢰 UID
    private String reqRmk;          // 의뢰 비고
    private Character reqCanlYn;    // 의뢰 취소 여부
    private String reqCanlDs;       // 의뢰 취소 일시
    private String reqCanlUid;      // 의뢰 취소 UID
    private String reqCanlRea;      // 의뢰 취소 사유
    private String ansNo;           // 시험 번호
    private String rcpDt;           // 접수 일자
    private String rcpDs;           // 접수 일시
    private String rcpDptCd;        // 접수 부서 코드
    private String rcpUid;          // 접수 UID
    private String rcpRmk;          // 접수 비고
    private String assNo;           // 지시 번호
    private String assDt;           // 지시 일자
    private String assDs;           // 지시 일시
    private String assSpcc;         // 지시 특이사항
    private Integer assAprReqIdx;   // 지시 승인 요청 IDX
    private String cplRqmDt;        // 완료 요구 일자
    private String wrkSttCd;        // U009 작업 상태 코드
    private String pdtPitmCd;       // 생산 품목 코드
    private String makNo;           // 제조 번호
    private String revwDt;          // 검토 일자
    private String revwDs;          // 검토 일시
    private String revwUid;         // 검토 UID
    private String revwCmmt;        // 검토 의견
    private Integer rstAprReqIdx;   // 결과 승인 요청 IDX
    private Character hldYn;        // 보류 여부
    private String hldDs;           // 보류 일시
    private String hldUid;          // 보류 UID
    private String hldRea;          // 보류 사유
    private String hldCanlDs;       // 보류 취소 일시
    private String hldCanlUid;      // 보류 취소 UID
    private String hldCanlRea;      // 보류 취소 사유
    private String ansCanlDs;       // 시험 취소 일시
    private String ansCanlUid;      // 시험 취소 UID
    private String ansCanlRea;      // 시험 취소 사유
    private String rjtUid;          // 반려 UID
    private String rjtDs;           // 반려 일시
    private String rjtRea;          // 반려 사유

    /* MT_MITM_ANS_RST(모니터링 항목 시험 결과) */
    private String plntCd;          // 사업장 코드
    private String mitmReqIdx;     // 모니터링 항목 코드 의뢰 IDX
    private Integer rstSeq;         // 결과 순번
    private String rstProcCd;       // S014결과 진행상태 코드
    private String ansDptCd;        // 시험 부서 코드
    private String amitmCd;         // 시험 항목 별 방법 코드
    private Integer aitmOrd;        // 시험 항목 정렬 순서
    private String perspecTxt;      // 허가 규격 텍스트
    private String owcSpecTxt;      // 자사 규격 텍스트
    private String specTxtEn;       // 규격 텍스트 영문
    private String specTyp;         // S006 규격 유형
    private String jdgTyp;          // S007 판정 유형
    private Integer perSlvLow;      // 허가 기준 하한
    private Integer perSlvUpp;      // 허가 기준 상한
    private String perSlvDesc;      // 허가 기준 서술
    private Integer owcSlvLow;      // 자사 기준 하한
    private Integer owcSlvUpp;      // 자사 기준 상한
    private String owcSlvDesc;      // 자사 기준 서술
    private String slvJdgCfm;       // U008 기준 판정 적함
    private String slvJdgNonCfm;    // U008 기준 판정 부적합
    private String rstUnitCd;       // U005 결과 단위 코드
    private Integer rstDpnt;        // 결과 소수점
    private String rptPrtSlvVal;    // 성적서 출력 기준 값
    private String rptPrtItmNm;     // 성적서 출력 항목 명
    private String rptPrtYn;        // 성적서 출력 여부
    private String ispDptCd;        // 검사 부서 코드
    private String rstVal;          // 결과 값
    private String markVal;         // 표기 값
    private String rstRmk;          // 결과 비고
    private Integer rstFileIdx;     // 결과 파일 IDX
    private String rstJdg;          // S012 결과 판정
    private Integer smpClltQty;     // 검체 채취 수량
    private String smpClltUnit;     // U005 검체 채취 단위
    private Integer ispDurTm;       // 검사 소요 시간
    private Integer eqmUseTm;       // 기기 사용 시간
    private String ansEqmCd;        // 분석 기기 코드
    private String ansUid;          // 시험 UID
    private String ansRstInpDs;     // 시험 결과 입력 일시
    private String smpClltDt;       // 검체 채취 일자
    private Integer clltTmStr;      // 채취 시간 시작
    private Integer clltTmEnd;      // 채취 시간 종료
    private Character delYn;        // 삭제 여부
    private Integer mkrQty;         // 표시량
    private String aitmRmk;         // 시험 항목 비고

    /* MM_MITM (MM 모니터링 항목)*/
    private String mitmCd;          // 모니터링 항목 코드
    private String mitmWrkPlcDiv;   // 모니터링 항목 작업실 구분
    private String mitmPitmDiv;     // 모니터링 항목 품목 구분
    private String point;           // 포인트
    private String roomno;          // RoomNo
    private String grade;           // U006 Grade
    private String ansStrDt;        // 시험 시작 일자
    private String ansCylCd;        // 시험 주기 코드
    private String crgDptCd;        // 담당 부서 코드
    private String perSpec;         // 허가 규격
    private String revwDurTm;       // 검토 소요 시간
    private String aprDurTm;        // 승인 소요 시간
    private Character useYn;        // 사용 여부

    /* MM_MITM_SPEC (MM 모니터링 항목 규격서)*/
    private String aitmSpecIdx;     // 모니터링 항목 규격서 IDX
    private String specProcCd;      // S008 규격서 진행 상태 코드
    private String useVerYn;        // 사용 버전 여부
    private String rvsNo;           // 개정 번호
    private String docNo;           // 문서 번호
    private String rvsDt;           // 개정 일자
    private String enfoDt;          // 시행 일자
    private String rvsReaCd;        // U007 개정 사유 코드
    private String rvsCtt;          // 개정 내역
    private String rvsDivPs;        // 개정 구분 PS
    private String revwIp;          // 검토 IP
    private String mitmSpecAprIdx;  //  모니터링 항목 규격 승인 IDX

    /* MS_AITM (MS 시험 항목) */
    private String aitmCd;          // 시험 항목 코드
    private String aitmKn;          // 시험 항목 국문
    private String aitmEn;          // 시험 항목 영문
    private String aitmAbbr;        // 시험 항목 약어
    private String vriaKn;
    private String vriaNo;

    /* SY_CD_TREE (SY 코드 계층) */
    private String treeCd;          // 계층 코드
    private String hirTreeCd;       // 상위 계층 코드
    private String treeNm;          // 계층 명
    private String treeEn;          // 계층 영문
    private String treeAbbr;        // 계층 약어
    private String treeCdOrd;       // 계층 코드 순서
    private String deptLmt;         // 뎁스 제한 (깊이 제한)
    private String treeRmk;         // 계층 비고

    /* Alias */
    private String upperMitmPitmDiv;
    private List<String> rcpDtList = new ArrayList<>();
    private List<String> reqDtList = new ArrayList<>();
    private String ansProcNm;
    private String reqDptNm;
    private String rjtNm;
    private String hldNm;
    private String andCanlNm;
    private String mitmWrkPlcNm;
    private String mtimWrkPlc;      // 작업실
    private String mitmWrkPlcDivNm;
    private String crgDptNm;
    private String ansEqmNm;
    private String smpClltUnitNm;
    private String rstJdgNm;
    private String rstUnitNm;
    private String slvJdgCfmNm;
    private String slvJdgNonCfmNm;
    private String ansDptNm;
    private String rstProcNm;
    private String testDiv;
    private String SpecTypNm;
    private String jdgTypNm;
    private String upperMitmWrkPlcDiv;
    private String ansCylNm;
    private String mitmPitmDivNm;
    private String hirCd;
    private String mitmWrkStudioDiv;
}
