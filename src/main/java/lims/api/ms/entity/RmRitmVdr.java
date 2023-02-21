package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RM_RITM_VDR")
public class RmRitmVdr {
    @AuditId
    private String plntCd;
    @AuditId
    private String vdrCd;
    private String vdrDiv;
    private String vdrNm;
    private String vdrCrgNm;
    private String vdrCrgTel;
    private String vdrAdr;
    private String vdrAdrDtl;
    private String delYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
