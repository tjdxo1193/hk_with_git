package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "MS_ELN_CT_RPT_FILE")
public class MsElnCtRptFile {
    @AuditId
    private String ctId;
    @AuditId
    private String ctSeq;
    private String fileName;
    private String crtDs;
    private String udtDs;
}
