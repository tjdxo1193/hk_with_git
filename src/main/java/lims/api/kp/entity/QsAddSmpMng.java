package lims.api.kp.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QS_ADD_SMP_MNG")
public class QsAddSmpMng {
    @AuditId
    private String plntCd;
    @AuditId
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
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
