package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_MATERIAL_MARA")
public class IFRevSapMaterialMara {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String matnr;
    private String mtart;
    private String meins;
    private String matkl;
    private String bismt;
    private String prdha;
    private String spart;
    private String mstae;
    private String wrkst;
    private String zeinr;
    private String taklv;
    private String bstme;
    private String xchpf;
    private String ekwsl;
    private String mhdhb;
    private String zlabno;
    private String zprodAbbr;


}