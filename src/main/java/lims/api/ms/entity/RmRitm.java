package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RM_RITM")
public class RmRitm {
    @AuditId
    private String plntCd;
    @AuditId
    private String ritmCd;
    private String ritmTreeCd;
    private String ritmKn;
    private String ritmEn;
    private String ritmAbbr;
    private String ritmRmk;
    private String ritmCrgUid;
    private String ritmUnitCd;
    private String strgTerms;
    private String strgPla;
    private String mngNo;
    private Integer sfyStok;
    private Integer opnAftExpirTrm;
    private String spec;
    private String casNo;
    private String fomu;
    private String shadeYn;
    private String mtrDedutYn;
    private String poisYn;
    private String dangYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private String useYn;
}