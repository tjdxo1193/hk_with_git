package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_BOM")
public class IFRevSapBom {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String guid;
    private String seq;
    private String parentGuid;
    private String matnr;
    private String maktx;
    private String mtart;
    private String werks;
    private String name1;
    private String dispo;
    private String dsnam;
    private String stlal;
    private String stktx;
    private String bmeng;
    private String bmein;
    private String validFrom;
    private String validTo;
    private String stlnr;
    private String ztext;
    private String lkenz;
    private String postp;
    private String ptext;
    private String posnr;
    private String idnrk;
    private String idnrkMakt;
    private String potx1;
    private String menge;
    private String meins;
    private String fmnge;
    private String beikz;
    private String crtDs;

}