package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_ANS_TRM_ITV")
public class SyAnsTrmItv {

    @AuditId
    private String plntCd;
    @AuditId
    private String ansTrmCd;
    @AuditId
    private String itvSeq;
    private String accRulNm;
    private String accMarkNm;
    private Integer ansAccVal;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

}