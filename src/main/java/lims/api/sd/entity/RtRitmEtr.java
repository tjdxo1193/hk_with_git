package lims.api.sd.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RT_RITM_ETR")
public class RtRitmEtr {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer ritmEtrIdx;
    private String ritmRootCd;
    private String ritmCd;
    private String ritmKn;
    private String ritmEn;
    private String ritmAbbr;
    private String ritmCrgUid;
    private String mngNo;
    private String etrProcCd;
    private String strgTerms;
    private String strgPla;
    private String strgPlaDtl;
    private String ritmUnitCd;
    private Integer sfyStok;
    private String etrQty;
    private String etrEachQty;
    private String etrTotQty;
    private String vdrCd;
    private String vdrCrgNm;
    private String vdrCrgTel;
    private String makDt;
    private String makNo;
    private String etrDt;
    private String etrRmk;
    private String expirDtChk;
    private String opnBefExpirDt;
    private Integer opnAftExpirTrm;
    private String currLotYn;
    private String etrReqAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String delUid;
    private String delDs;
    private String delRea;
    private String delYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private String spec;
    private String casNo;
    private String fomu;
    private String shadeYn;
    private String mtrDedutYn;
    private String poisYn;
    private String dangYn;
}
