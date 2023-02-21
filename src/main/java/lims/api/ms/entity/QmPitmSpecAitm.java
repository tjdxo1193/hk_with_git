package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QM_PITM_SPEC_AITM")
public class QmPitmSpecAitm {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer aitmSpecIdx;
    @AuditId
    private String aitmSeq;
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
    private String smpClltQty;
    private String smpClltUnit;
    private String ispDurTm;
    private String eqmUseTm;
    private String aitmRmk;
    private String mkrQty;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

}
