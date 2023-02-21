package lims.api.pr.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrintLabelVO implements UpdateDetect {
    /* QT_PITM_ANS_PROC */
    private String plntCd;              // 사업장 코드
    private Integer ansIdx;             // 시험 IDX
    private Integer pitmSpecIdx;        // 규격서 IDX
    private String ansProcCd;           // S013 시험 진행상태 코드
    private String ansProcCdNm;         // 시험 진행상태 코드명(임의)
    private String ansTyp;              // S023, 시험 유형
    private String ansTypNm;            // 시험 유형명(임의추가)
    private String ansNo;               // 시험 번호
    private String reqDt;               // 의뢰 일자
    private String rcpDt;               // 접수 일자
    private String clltDt;              // 채취 일자
    private String clltUid;             // 채취 UID
    private String clltUidNm;           // 채취자(임의추가)
    private String assDt;               // 지시 일자
    private String reqRmk;              // 의뢰 비고


    /* QT_PITM_ANS_REQ */
    private String mtrCd;               // 자재 코드
    private String batchNo;             // 배치 번호
    private String etrQty;              // 입고 수량
    private String inpUnit;             // 입력 단위
    private String splCd;               // 공급사 코드
    private String splNm;               // 공급사 명
    private String lotNo;               // 제조번호
    private String splLotNo;            // 공급사 제조번호
    private String makDt;               // 제조 일자
    private String useLmt;              // 사용 기한
    private String csmNm;               // 고객처 명
    private String etrDt;               // 입고 일자

    /* QS 검체 라벨 출력, QS_SMP_LABEL_PRT */
    private Integer labelPrtIdx;        // 라첼 출력 IDX
    private Integer prtSeq;             // 출력 순번
    private Integer labelRptIdx;        // 라벨 리포트 IDX
    private String labelPrtProc;        // S035, 라벨 출력 프로세스
    private String labelPrtProcNm;      // 라벨 출력 프로세스명(임의추가)
    private Integer labelRePrtAprIdx;   // 라벨 재출력 승인 IDX
    private String labelRePrtRjtUid;    // 라벨 재출력 반려 UID
    private String labelRePrtRjtUidNm;  // 라벨 재출력 반려자(임의추가)
    private String labelRePrtRjtDs;     // 라벨 재출력 반려 일시
    private String labelRePrtRjtDt;     // 라벨 재출력 반려 일자(임의추가)
    private String labelRePrtRjtRea;    // 라벨 재출력 반려 사유
    private String prtUid;             // 출력 UID
    private String prtUidNm;           // 출력자(임의추가)
    private String prtDs;               // 출력 일시

    /* QT 품목 시험 정보, QT_PITM_ANS_INFO */
    private Integer smpLabelPrtIdx;     // 검체 라벨 출력 IDX

    /* SY 리포트 마스터, SY_RPT_MST */
    private Integer rptIdx;             // 리포트 IDX
    private String rptDiv;              // S024, 리포트 구분
    private String rptDivNm;            // 리포트 구분명(임의추가)
    private String rptNm;               // 리포트 명
    private String rptRdPath;           // 리포트 RD 경로
    private String rptRmk;              // 성적서 비고
    private String etcRmk;              // 기타 비고

    /* QM 품목 규격서, QM_PITM_SPEC */
    private String specProcCd;          // S008, 규격서 진행상태 코드

    /* QM 품목 정보, QM_PITM_INFO */
    private String pitmCd;              // 품목 코드
    private String pitmVer;             // 품목 버전
    private String pitmTyp;             // S018, 품목 유형
    private String pitmTypNm;           // 품목 유형명(임의추가)
    private String pitmNm;              // 품목 명

    /* QS 검체 관리, QS_SMP_MNG */
    private String smpDivCd;            // S026, 검체 구분 코드
    private String smpDivCdNm;          // 검체 구분 코드명

    /* Etc. */
    private String loginUserUid;        // 로그인 유저 UID
    private String pitmCdForDecode;     // 쿼리 DECODE 용, 품목코드
    private String crtNo;               // 생산 번호(pitmCdForDecode와 DECODE를 이용한 결과)

    private String smpVolTot;          // 총검체량
    private List<String> searchRcpDt;   // 접수 일자 조회조건
}
