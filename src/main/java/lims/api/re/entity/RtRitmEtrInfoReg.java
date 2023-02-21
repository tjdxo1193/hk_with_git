package lims.api.re.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RT_RITM_ETR_INFO_REG")
public class RtRitmEtrInfoReg {
    @AuditId
    private String plntCd;
    @AuditId
    private String ritmEtrIdx;
    private String regRnk;
    private String regVolUnitCd;
    private String regCtetUnitCd;
    private String regFact;
    private String regVol;
    private String regCtet;
}
