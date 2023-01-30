package lims.api.st.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StabPlanDetailApprVO {
    // ST_SBT_PLN
    private String plntCd;                      // 사업장 코드
    private Integer sbtPlnIdx;                  // 안정성 계획 IDX
    private String sbtAnsProc;                  // S029 안정성 시험 프로세스
    private String sbtAnsProcNm;                // 안정성 시험 프로세스명(임의추가)
    private String sbtPlnStt;                   // S030 안정성 계획 상태
    private String sbtPlnSttNm;                 // 안정성 계획 상태명(임의추가)
    private String delYn;                       // 삭제 여부
    private String sbtAnsPlnNo;                 // 안정성 시험 계획 번호
    private String sbtAnsRptNo;                 // 안정성 시험 보고서 번호
    private Integer ansIdx;                     // 시험 IDX
    private String ansKnd;                      // U021 시험 종류
    private String ansKndNm;                    // U021 시험 종류명(임의추가)
    private String ansPps;                      // U022 시험 목적
    private String ansPpsNm;                    // U022 시험 목적명(임의추가)
    private String ansPpsDtl;                   // 시험 목적 상세
    private String strgTerms;                   // U023 보관 조건
    private String strgTermsNm;                 // 보관 조건명(임의추가)
    private String strgPla;                     // U024 보관 장소
    private String strgPlaNm;                   // 보관 장소명(임의추가)
    private String smpVolUnit;                  // U005 검체량 단위
    private String smpVolUnitNm;                // 검체량 단위명(임의추가)
    private String inpUnit;                     // 입력 단위
    private Integer sbtSmpVol;                  // 안정성 검체량
    private String docNo;                       // 문서 번호
    private String ansStrDt;                    // 시험 시작 일자
    private String ansTrmCd;                    // 시험 기간 코드
    private String ansTrmMarkNm;                // 시험 기간 표기 명(SY_ANS_TRM)
    private String sbtCrgUid;                   // 안정성 담당 UID
    private String sbtCrgUidNm;                 // 안정성 담당자 명(임의추가)
    private String rmk;                         // 비고
    private Integer sbtAnsPlnAprIdx;            // 안정성 시험 계획 승인 IDX
    private String plnRjtUid;                   // 계획 반려 UID
    private String plnRjtUidNm;                 // 계획 반려자(임의추가)
    private String plnRjtDs;                    // 계획 반려 일시
    private String plnRjtDt;                    // 계획 반려 일자(임의추가)
    private String plnRjtRea;                   // 계획 반려 사유
    private Integer sbtAnsPlnChgAprIdx;         // 안정성 시험 계획 변경 승인 IDX
    private String plnChgRjtUid;                // 계획 변경 반려 UID
    private String plnChgRjtUidNm;              // 계획 변경 반려자(임의추가)
    private String plnChgRjtDs;                 // 계획 변경 반려 일시
    private String plnChgRjtRea;                // 계획 변경 반려 사유
    private String sbtAnsRstAprUid;             // 안정성 시험 결과 승인 UID
    private String sbtAnsRstAprUidNm;           // 안정성 시험 결과 승인자(임의추가)
    private String sbtAnsRstAprDs;              // 안정성 시험 결과 승인 일시
    private String udtIp;                       // 수정 IP
    private String udtDs;                       // 수정 일시
    private String udtUid;                      // 수정 UID
    private String udtUidNm;                    // 수정자(임의추가)

    // ST_SBT_ANS
    private String sbtAnsIdx;                   // 안정성 시험 IDX
    private String sbtAnsStt;                   // S031 안정성 시험 상태
    private String sbtAnsSttNm;                 // 안정성 시험 상태명
    private String ansDelYn;                    // 시험 삭제 여부
    private String ansDt;                       // 시험 일자
    private String accMarkNm;                   // 누적 표기 명
    private String ansSmpVol;                   // 시험 검체량

    // ST_SBT_ANS_AITM
    private Integer aitmSeq;                    // 시험항목 순번
    private String amitmCd;                     // 시험항목별방법 코드

    // MS_AITM
    private String aitmCd;                      // 시험항목 코드
    private String aitmKn;                      // 시험항목 국문

    // QM_PITM_INFO
    private String pitmCd;                      // 품목코드
    private String pitmNm;                      // 품목 명

    // QT_PITM_ANS_REQ
    private String lotNo;                       // 제조번호
    private String makDt;                       // 제조일자

    // QT_PITM_ANS_PROC
    private String ansNo;                       // 시험 번호
    private String clltDt;                      // 채취 일자
    private String clltUid;                     // 채취 UID
    private String clltUidNm;                   // 채취자
    private String ansEdt;                      // 시험 예정일

    // Etc.
    private String loginUserUid;
    private List<String> sbtAnsProcList;        // S029, 안정성 시험 프로세스 조회용 리스트
    private List<String> ansEdtBetween;         // 시험 예정일 리스트 조회용
}
