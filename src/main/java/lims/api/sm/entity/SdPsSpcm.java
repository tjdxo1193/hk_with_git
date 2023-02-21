package lims.api.sm.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SD_PS_SPCM")
public class SdPsSpcm {
        @AuditId
        private String plntCd;
        @AuditId
        private Integer psSpcmIdx;
        private String pitmVer;
        private String pitmCd;
        private Integer pdtNo;
        private String enmDt;
        private String expirDt;
        private String aprUid;
        private String muft;
        private String rmk;
        private String useYn;
        private String crtIp;
        private String crtDs;
        private String crtUid;
        private String udtIp;
        private String udtDs;
        private String udtUid;
}
