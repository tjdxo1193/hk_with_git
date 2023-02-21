package lims.api.sm.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SD_SPCM")
public class SdSpcm {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer spcmIdx;
    private String pitmVer;
    private String pitmCd;
    private Integer revNo;
    private String enmDt;
    private String expirDt;
    private String aprUid;
    private String muft;
    private String rmk;
    private String useYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
}
