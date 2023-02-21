package lims.api.sm.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SD_PM_SPCM_ACSR")
public class SdPmSpcmAcsr {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer pmSpcmIdx;
    @AuditId
    private Integer acsrSeq;
    private String pitmVer;
    private String pitmCd;
    private String enmDt;
    private String expirDt;
    private String aprUid;
    private String muft;
    private String rmk;
    private String delYn;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
