package lims.api.sm.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SD_PM_SPCM")
public class SdPmSpcm {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer pmSpcmIdx;
    private String pkgMtrDiv;
    private Integer pmSpcmDelAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

}
