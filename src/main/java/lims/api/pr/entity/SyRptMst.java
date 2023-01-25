package lims.api.pr.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_RPT_MST")
public class SyRptMst {
    
    @AuditId
    private Integer rptIdx;
    private String rptDiv;
    private String rptNm;
    private String rptRdPath;
    private String rptRmk;
    private String etcRmk;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
