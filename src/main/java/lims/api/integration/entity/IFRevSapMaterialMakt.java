package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_MATERIAL_MAKT")
public class IFRevSapMaterialMakt {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String matnr;
    private String spras;
    private String maktx;

}