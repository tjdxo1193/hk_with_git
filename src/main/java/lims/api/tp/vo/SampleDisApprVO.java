package lims.api.tp.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleDisApprVO {
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
    private String mngSmpVol;       // 관리 검체량
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

    /* 공통 승인 정보 */
    private String aprReqDiv;       // S005 승인 요청 구분
    private String aprReqUid;       // 승인 요청 UID
    private String aprReqDs;        // 승인 요청 일시
    private String aprReqIp;        // 승인 요청 IP
    private String aprReqRea;       // 승인 요청 사유
    private String aprUid;      // 승인자 UID
    private String aprDs;       // 승인 일시
    private String aprIp;       // 승인 IP
    private String aprRea;      // 승인 사유
    private String aprYn;       // 승인 여부

    /* 별칭 */
}
