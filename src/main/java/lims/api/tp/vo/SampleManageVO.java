package lims.api.tp.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SampleManageVO {
    /* QS 검체 관리 */
    private String plntCd;          // 사업장 코드
    private Integer smpMngIdx;      // 검체 관리 IDX
    private String smpDpsProc;      // S027 검체 폐기 프로세스
    private String pitmTyp;         // S018 품목 유형
    private String pitmCd;          // 품목 코드
    private String pitmNm;          // 품목 명
    private String ansTyp;          // S023 시험 유형
    private Integer ansIdx;         // 시험 IDX
    private String smpDivCd;        // S026 검체 구분 코드
    private Integer mngSmpVol;       // 관리 검체량
    private String smpStrgMtd;      // U014 검체 보관 방법
    private String smpVolUnit;      // U015 검체량 단위
    private String lotNo;           // 제조 번호
    private String batchNo;         // 배치 번호
    private String inpUnit;         // 입력 단위
    private String makDt;           // 제조 일자
    private String strgLmt;         // 보관 기한
    private String useLmt;          // 사용 기한
    private Character irgYn;        // 이상 여부
    private String smpRmk;          // 검체 비고
    private String dpsExpDt;        // 폐기 예정 일자
    private String dpsFixDt;        // 폐기 확정 일자
    private Integer smpDpsAprIdx;   // 검체 폐기 승인 IDX
    private String dpsRea;          // 폐기 사유
    private String dpsCanRea;       // 폐기 취소 사유
    private String rjtUid;          // 반려 UID
    private String rjtDs;           // 반려 일시
    private String rjtRea;          // 반려 사유
    private Character delYn;        // 삭제 여부
    private Character dpsYn;        // 폐기 여부
    private String smpEtrDt;        // 검체 입고 일자
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

    /* QT 시험 품목 조회 */
    private String reqIdx;
    private String rcpDt;
    private String ansProcCd;
    private String reqDt;
    private String ansNo;

    /* 별칭 */
    private String smpDpsNm;        // 검체 폐기 프로세스 명칭
    private String pitmTypNm;       // 품목 유형 명칭
    private String ansTypNm;        // 시험 유형 명칭
    private String mngSmpVolNm;     // 검체 구분 명칭
    private String smpStrgMtdNm;    // 검체 보관 방법 명칭
    private String rjtNm;           // 반려자 명칭
    private List<String> dpsExpDtList = new ArrayList<>();  // 폐기 예정 일자 리스트
    private List<String> rcpDtList = new ArrayList<>();     // 시험 접수 일자 리스트
    private List<String> smpEtrDtList = new ArrayList<>();  // 검체 입고 일자 리스트
    private String smpDivNm;        // 검체 구분 명칭

}
