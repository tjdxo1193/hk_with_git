package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_ANS_TRM")
public class SyAnsTrm {

    @AuditId
    private String plntCd;
    @AuditId
    private String ansTrmCd;
    private String ansTrmMarkNm;
    private String ansCylDiv;
    private Integer ansTrm;
    private Integer ansItv;
    private String useYn;
    private String delYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;


}