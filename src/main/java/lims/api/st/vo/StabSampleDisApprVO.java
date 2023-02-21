package lims.api.st.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StabSampleDisApprVO {
    /* QS_SMP_MNG (QS 검체 관리 테이블) */
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
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

    /* 품목 시험 */
    private String ansNo;

    /* 공통 승인 정보 */
    private String aprReqDiv;   // S005 승인 요청 구분
    private String aprReqUid;   // 승인 요청 UID
    private String aprReqDs;    // 승인 요청 일시
    private String aprReqIp;    // 승인 요청 IP
    private String aprReqRea;   // 승인 요청 사유
    private String aprUid;      // 승인자 UID
    private String aprDs;       // 승인 일시
    private String aprIp;       // 승인 IP
    private String aprRea;      // 승인 사유
    private String aprYn;       // 승인 여부

    /* 별칭 */
    private String pitmTypNm;       // 품목 구분 명칭
    private String smpDivNm;
    private Integer remains;
    private String smpDpsNm;
    private String ansTypNm;
    private List<String> useLmtDtList = new ArrayList<>();
}
