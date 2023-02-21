package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_ATH_GP")
public class SyAthGp {
    @AuditId
    private String plntCd;
    @AuditId
    private String athCd;
    @AuditId
    private String menuCd;
    private Character useYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
