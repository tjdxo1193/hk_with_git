package lims.api.ts.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QT_PITM_ANS_RST")
public class QtPitmAnsRst {
    @AuditId
    private Integer ansIdx;
    @AuditId
    private Integer rstSeq;
    @AuditId
    private String plntCd;
    private String rstProcCd;
    private String ansDptCd;
    private String amitmCd;
    private String aitmOrd;
    private String perspecTxt;
    private String owcSpecTxt;
    private String specTxtEn;
    private String specTyp;
    private String jdgTyp;
    private String perSlvLow;
    private String perSlvUpp;
    private String perSlvDesc;
    private String owcSlvLow;
    private String owcSlvUpp;
    private String owcSlvDesc;
    private String slvJdgCfm;
    private String slvJdgNonCfm;
    private String rstUnitCd;
    private String rstDpnt;
    private String rptPrtSlvVal;
    private String rptPrtItmNm;
    private String rptPrtYn;
    private String ispDptCd;
    private String rstVal;
    private String markVal;
    private String rstRmk;
    private Integer rstFileIdx;
    private String rstJdg;
    private String smpClltQty;
    private String smpClltUnit;
    private String ispDurTm;
    private String eqmUseTm;
    private String ansEqmCd;
    private String ansUid;
    private String ansRstInpDs;
    private String smpClltDt;
    private String clltTmStr;
    private String clltTmEnd;
    private String delYn;
    private String udtIp;
    private String udtDs;
    private String udtUid;

}
