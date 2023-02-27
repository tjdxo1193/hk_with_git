package lims.api.ts.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "MS_SRM_REO_PREV")
public class MsSrmReoPrev {
    @AuditId
    private String batchNo;
    @AuditId
    private Integer seq;
    private String phsOrderNo;
    private String pdtOrderNo;
    private String makNo;
    private String fileName;
    private String fileId;
    private String crtDs;
    private Integer ifInfoIdx;
    private String delYn;
}
