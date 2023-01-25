package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QM_PITM_SPEC_VRIB")
public class QmPitmSpecVrib {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer aitmSpecIdx;
    @AuditId
    private Integer aitmSeq;
    @AuditId
    private Integer vribSeq;
}
