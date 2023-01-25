package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_CD_HIR")
public class SyCdHir {
    @AuditId
    private String plntCd;
    @AuditId
    private String hirCd;
    private String cdDivSu;
    private String hirCdNm;
    private String hirCdOrd;
    private String useYn;
    private String hirDesc;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}