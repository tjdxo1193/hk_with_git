package lims.api.pr.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrintLabelVO implements UpdateDetect {
    /* QS_ADD_SMP_MNG */
    private String plntCd;              // 사업장 코드
    private Integer addSmpIdx;          // 추가 검체 IDX
    private Integer ansIdx;             // 시험 IDX
    private Integer smpMngIdx;          // 검체 관리 IDX
    private String addSmpProc;          // S032 추가 검체 프로세스
    private String addSmpProcNm;        // 추가 검체 프로세스명(임의추가)
    private String pitmTyp;             // S018 품목 유형
    private String pitmTypNm;           // 품목 유형명
    private String pitmCd;              // 품목 코드
    private String pitmNm;              // 품목 명
    private String ansTyp;              // S023 시험 유형
    private String ansTypNm;            // 시험 유형 명(임의추가)
    private String lotNo;               // 제조 번호
    private String batchNo;             // 배치 번호
    private String inpUnit;             // 입력 단위
    private String smpVolUnit;          // U005 검체량 단위
    private String smpVolUnitNm;        // 검체량 단위명(임의추가)
    private Integer reqSmpVol;          // 요청 검체량
    private String smpReqRea;           // U025 검체 요청 사유
    private String smpReqReaNm;         // 검체 요청 사유명(임의추가)
    private String smpReqReaDtl;        // 검체 요청 사유 상세
    private String labelCd;            // 라벨 코드
    private Character delYn;            // 삭제 여부
    private String delRea;              // 삭제 사유
    private Integer addSmpAprReqIdx;    // 추가 검체 승인 요청 IDX
    private String smpReqDt;            // 검체 요청 일자
    private String rjtUid;              // 반려 UID
    private String rjtDs;               // 반려 일시
    private String rjtRea;              // 반려 사유
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

    /* QS_SMP_MNG */
    private String smpDpsProc;          // S027, 검체 폐기 프로세스
    private String smpDpsProcNm;        // 검체 폐기 프로세스명(임의추가)
    private String smpDivCd;            // S026, 검체 구분 코드
    private String smpDivCdNm;          // 검체 구분 코드명(임의추가)
    private Integer mngSmpVol;          // 관리 검체량
    private String smpStrgMtd;          // U014, 검체 보관 방법
    private String smpStrgMtdNm;        // 검체 보관 방법명(임의추가)
    private String makDt;               // 제조 일자
    private String useLmt;              // 사용 기한
    private String smpRmk;              // 검체 비고

    /* QT_PITM_ANS_PROC */
    private String ansNo;               // 시험 번호
    private String reqDt;               // 의뢰 일자
    private String rcpDt;               // 접수 일자
    private String clltDt;              // 채취 일자
    private String clltUid;             // 채취 UID
    private String clltUidNm;           // 채취자(임의추가)
    private String assDt;               // 지시 일자
    private String ansProcCd;           // S013 시험 진행상태 코드
    private String ansProcCdNm;         // 시험 진행상태 코드명(임의)

    /* SMP_VOL_TOT */
    private String smpVolTot;           // 총검체량(임의추가)

    /* QT_PITM_ANS_REQ */
    private String mtrCd;               // 자재 코드
    private String splCd;               // 공급사 코드
    private String splNm;               // 공급사 명
    private String splLotNo;            // 공급사 제조번호
    private String csmNm;               // 고객처 명
    private String etrDt;               // 입고 일자
    private String etrQty;              // 입고 수량

    /* QS 검체 라벨 출력, QS_SMP_LABEL_PRT */
    private Integer labelPrtIdx;        // 라첼 출력 IDX
    private Integer prtSeq;             // 출력 순번
    private Integer labelRptIdx;        // 라벨 리포트 IDX
    private String labelPrtProc;        // S035, 라벨 출력 프로세스
    private String labelPrtProcNm;      // 라벨 출력 프로세스명(임의추가)
    private Integer labelRePrtAprIdx;   // 라벨 재출력 승인 IDX
    private Integer labelRePrtRjtUid;   // 라벨 재출력 반려 UID
    private String labelRePrtRjtDs;     // 라벨 재출력 반려 일시
    private String labelRePrtRjtRea;    // 라벨 재출력 반려 사유
    private Integer prtUid;             // 출력 UID
    private Integer prtUidNm;           // 출력자(임의추가)
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

    /* Etc. */
    private String loginUserUid;        // 로그인 유저 UID
    private String pitmCdForDecode;     // 쿼리 DECODE 용, 품목코드
    private String crtNo;               // 생산 번호(pitmCdForDecode와 DECODE를 이용한 결과)
    private List<String> searchRcpDt;   // 접수 일자 조회조건
}
