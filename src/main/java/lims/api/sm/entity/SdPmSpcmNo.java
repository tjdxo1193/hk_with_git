package lims.api.sm.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SD_PM_SPCM_NO")
public class SdPmSpcmNo {
    @AuditId
    private String pmSpcmNo;
    private String delAprYn;
}
