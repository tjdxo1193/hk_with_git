package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_ANS_CYL")
public class SyAnsCyl {
    @AuditId
    private String plntCd;
    @AuditId
    private String ansCylCd;
    private String ansCylRuleNm;
    private String ansCylMarkNm;
    private String ansCylDiv;
    private String ansItv;
    private String delYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}