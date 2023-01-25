package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_MATERIAL_ZMDV")
public class IFRevSapMaterialZmdv {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String matnr;
    private String classType;
    private String clazz;
    private String charCode;
    private String charDataTyp;
    private String charValChar;
    private String charValNum;

}