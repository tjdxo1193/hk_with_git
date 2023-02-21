package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_ATH")
public class SyAth {
    @AuditId
    private String plntCd;
    @AuditId
    private String athCd;
    private String athNm;
    private String athdesc;
    private Integer ord;
    private String useYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private Character ansYn;
    private Character revwYn;
    private Character aprYn;
}
