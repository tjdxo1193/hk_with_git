package lims.api.in.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "EM_EQM_ACSR")
public class EmEqmAcsr {
    @AuditId
    private String plntCd;
    @AuditId
    private String eqmCd;
    @AuditId
    private Integer acsrSeq;
    private String acsrNm;
    private String acsrModNm;
    private String acsrSrlNo;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private Character delYn;
}
