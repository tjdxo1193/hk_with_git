package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;
import spring.audit.annotation.AuditJoin;

@AuditEntity(name = "SY_ATH")
public class SyAth {
    @AuditId
    @AuditJoin(entity = SyPlnt.class, definition = "nameByCode")
    private String plntCd;
    @AuditId
    private String athCd;
    private String athNm;
    private String athdesc;
    private Integer ord;
    private String useYn;
    private String crtIp;
    private String crtDs;
    @AuditJoin(entity = SyUser.class, definition = "nameById")
    private String crtUid;
    private String udtIp;
    private String udtDs;
    @AuditJoin(entity = SyUser.class, definition = "nameById")
    private String udtUid;
    private Character ansYn;
    private Character revwYn;
    private Character aprYn;
}