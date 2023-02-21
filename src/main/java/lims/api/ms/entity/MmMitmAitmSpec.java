package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "MM_MITM_AITM_SPEC")
public class MmMitmAitmSpec {
    @AuditId
    private String plntCd;
    @AuditId
    private String aitmSpecIdx;
    private String aitmSpecVer;
}
