package lims.api.an.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RT_RITM_ETR_INFO_ANC")
public class RtRitmEtrInfoAnc {
    @AuditId
    private String plntCd;
    @AuditId
    private String ritmEtrIdx;
    private String ancCatNo;
    private String ancSrlNo;
    private String ancEtrNo;
    private String ancFpak;
    private String ancLen;
    private String ancLenUnitCd;
    private String ancInsDimt;
    private String ancPatcsize;
    private String ancUspCd;
    private String ancColNo;
    private String ancPitmDivCd;
    private String ancPitmNm;
    private String ancAitmNm;
    private String ancInitAns;
    private String ancRes;
    private String ancTai;
    private String ancThe;
    private String ancDevNo;
}
