package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;
import spring.audit.annotation.AuditJoinDefinition;

@AuditEntity(name = "SY_PLNT")
@AuditJoinDefinition(name = "nameByCode", joinProperty = "plntCd", auditProperty = "plntNm")
public class SyPlnt {

    @AuditId
    private String plntCd;
    private String plntNm;
    private String useYn;

}