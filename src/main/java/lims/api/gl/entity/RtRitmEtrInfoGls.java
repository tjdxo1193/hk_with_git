package lims.api.gl.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RT_RITM_ETR_INFO_GLS")
public class RtRitmEtrInfoGls {
    @AuditId
    private String plntCd;
    @AuditId
    private String glsVol;
    private String glsVolUnitCd;
    private String glsColrMtr;
}
