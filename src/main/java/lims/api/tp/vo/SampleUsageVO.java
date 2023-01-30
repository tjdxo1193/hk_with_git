package lims.api.tp.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleUsageVO {
    /* QS 검체 관리 */
    private String plntCd;          // 사업장 코드
    private Integer smpMngIdx;      // 검체 관리 IDX
    private String smpDpsProc;      // S027 검체 폐기 프로세스
    private String pitmTyp;         // S018 품목 유형
    private String pitmCd;          // 품목 코드
    private String pitmNm;          // 품목 명
    private String ansTyp;          // S023 시험 유형
    private Integer ansIdx;         // 시험 IDX
    private String mngSmpVol;       // S026 검체 구분 코드
    private String smpStrgVol;      // 관리 검체량
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
    private String smpDivCd;

    /* QS_SMP_USE(QS 검체 사용) */
    private Integer useSeq;         // 사용 순번
    private String smpUseProc;      // S028 검체 사용 프로세스
    private String useSmpVol;       // 사용 검체량
    private String usePps;          // 사용 목적
    private String useUid;          // 사용 UID
    private String useDt;           // 사용 일자
    private String strgPla;         // 보관 장소
    private String delRea;          // 삭제 사유
    private Integer smpUseAprIdx;   // 검체 사용 승인 IDX

    /* 공통 승인 정보*/
    private String aprReqDiv;       // 승인 정보 구분
    private String aprUid;          // 승인 UID
    private String aprReqUid;       // 승인 요청 UID

    /* 별칭 */
    private String smpUseNm;        // 검체 사용 프로세스 명칭
    private String useNm;           // 사용자 명칭
    private String rjtNm;           // 반려자 명칭
    private String smpDpsNm;        // 검체 폐기 프로세스 명칭
    private String pitmTypNm;       // 품목 유형 명칭
    private String ansTypNm;        // 시험 유형 명칭
    private String smpStrgMtdNm;    // 검체 보관 방법 명칭
    private String smpVolUnitNm;    // 검체량 단위 명칭
    private String smpDivNm;        // 검체 구분 명칭
    private String ansNo;           // 시험 번호
    private String smpTypNm;        // 검체 구분 명칭
    private String sumVol;          // 사용 검체량 총합
    private String remains;         // 사용하고 남은 검체
}
