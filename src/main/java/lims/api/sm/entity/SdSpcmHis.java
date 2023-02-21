package lims.api.sm.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SD_SPCM_HIS")
public class SdSpcmHis {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer spcmIdx;
    @AuditId
    private Integer hisSeq;
    private String hisRevNo;
    private String chgRea;
    private String udtHis;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
