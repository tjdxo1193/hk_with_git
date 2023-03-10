package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_DPT")
public class DepartmentManageEntity {
    @AuditId
    private String plntCd;
    @AuditId
    private String dptCd;
    private String hirDptCd;
    private String dptNm;
    private String dptAbbr;
    private String rmk;
    private String useYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}