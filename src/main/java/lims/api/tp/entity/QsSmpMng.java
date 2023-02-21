package lims.api.tp.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QS_SMP_MNG")
public class QsSmpMng {
    @AuditId
    private String plntCd;
    @AuditId
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
}
