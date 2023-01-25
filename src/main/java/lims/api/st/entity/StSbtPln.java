package lims.api.st.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "ST_SBT_PLN")
public class StSbtPln {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer sbtPlnIdx;
    private String sbtAnsProc;
    private String sbtPlnStt;
    private String delYn;
    private String sbtAnsPlnNo;
    private String ansKnd;
    private String ansPps;
    private String ansPpsDtl;
    private String strgTerms;
    private String strgPla;
    private String smpVolUnit;
    private String inpUnit;
    private Integer sbtSmpVol;
    private String docNo;
    private String ansStrDt;
    private String ansTrmCd;
    private String sbtCrgUid;
    private String rmk;
    private Integer ansIdx;
    private Integer sbtAnsPlnAprIdx;
    private String plnRjtUid;
    private String plnRjtDs;
    private String plnRjtRea;
    private Integer sbtAnsPlnChgAprIdx;
    private String plnChgRjtUid;
    private String plnChgRjtDs;
    private String plnChgRjtRea;
    private String sbtAnsRstAprUid;
    private String sbtAnsRstAprDs;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
