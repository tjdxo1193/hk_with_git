package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_MATERIAL_MVKE")
public class IFRevSapMaterialMvke {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String matnr;
    private String vkorg;
    private String vtweg;
    private String dwerk;
    private String aumng;
    private String ktgrm;

}