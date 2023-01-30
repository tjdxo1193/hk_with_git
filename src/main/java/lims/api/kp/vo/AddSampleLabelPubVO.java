package lims.api.kp.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSampleLabelPubVO {
    /*  추가 검체 관리 */
    private String plntCd;
    private Integer addSmpIdx;
    private Integer ansIdx;
    private Integer smpMngIdx;
    private String addSmpProc;
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

    /* 별칭 */
    private String addSmpNm;
    private String pitmTypNm;
    private String ansTypNm;
    private String smpReqReaNm;
}
