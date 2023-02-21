package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QM_PITM_AITM_SPEC")
public class QmPitmAitmSpec {
    @AuditId
    private String plntCd;
    @AuditId
    private String aitmSpecIdx;
    private String aitmSpecVer;
}
