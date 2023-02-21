package lims.api.st.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "ST_SBT_ANS_AITM")
public class StSbtAnsAitm {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer sbtAnsIdx;
    @AuditId
    private Integer aitmSeq;
    private String amitmCd;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

}
