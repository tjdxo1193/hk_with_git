package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_PLNT")
public class SyPlnt {

    @AuditId
    private String plntCd;
    private String plntNm;
    private String useYn;

}