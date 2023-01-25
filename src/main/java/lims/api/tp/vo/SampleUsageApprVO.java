package lims.api.tp.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleUsageApprVO {
    /* QS 검체 관리 */
    private String plntCd;
    private Integer smpMngIdx;
    private String smpDpsProc;
    private String pitmTyp;
    private String pitmCd;
    private String pitmNm;
    private String ansTyp;
    private Integer ansIdx;
    private String smpDivCd;
    private Integer mngSmpVol;
    private String smpStrgMtd;
    private String smpVolUnit;
    private String lotNo;
    private String batchNo;
    private String inpUnit;
    private String makDt;
    private String strgLmt;
    private String useLmt;
    private Character irgYn;
    private String smpRmk;
    private String dpsExpDt;
    private String dpsFixDt;
    private Integer smpDpsAprIdx;
    private String dpsRea;
    private String dpsCanlRea;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private Character delYn;
    private Character dpsYn;
    private String smpEtrDt;

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
    private String smpUseNm;
    private String useNm;
    private String pitmTypNm;
    private String ansNo;
}
