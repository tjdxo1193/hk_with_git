package lims.api.kp.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddSampleLabelPubVO {
    /*  추가 검체 관리 */
    private String plntCd;
    private Integer addSmpIdx;
    private Integer ansIdx;
    private Integer smpMngIdx;
    private String addSmpProc;              // S032, 추가 검체 프로세스
    private String addSmpProcNm;            // 추가 검체 프로세스명(임의추가)
    private String pitmTyp;
    private String pitmCd;
    private String pitmNm;
    private String ansTyp;
    private String lotNo;
    private String batchNo;
    private String inpUnit;
    private String smpVolUnit;
    private Integer reqSmpVol;
    private String smpReqRea;
    private String smpReqReaDtl;
    private String labelCd;
    private Character delYn;
    private String delRea;
    private Integer addSmpAprReqIdx;
    private String smpReqDt;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;

    /* QT_PITM_ANS_PROC, QT 품목 시험 프로세스 */
    private String ansNo;               // 시험 번호

    /* Etc. */
    private String pitmTypNm;
    private String ansTypNm;
    private String smpReqReaNm;
    private List<String> searchSmpReqDt;
}
