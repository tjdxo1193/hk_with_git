package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_TEST_REQUEST_HEADER")
public class IFRevSapTestRequestHeader {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String guid;
    private String zqcreqno;
    private String werks;
    private String matnr;
    private String maktx;
    private String charg;
    private String erfmg;
    private String erfme;
    private String lgort;
    private String budat;
    private String bsart;
    private String ebeln;
    private String ebelp;
    private String pstyp;
    private String netpr;
    private String peinh;
    private String waers;
    private String dmbtr;
    private String menge;
    private String meins;
    private String lifnr;
    private String name1;
    private String afnam;
    private String zparcel;
    private String lfdat;
    private String zdeliverytime;
    private String zcoa;
    private String zlotNo;
    private String zaltBomNo;
    private String zvndLotNo;
    private String hsdat;
    private String zstoringEndDate;
    private String zshelfLifeEndDate;
    private String auart;
    private String aufnr;
    private String zsubconno;
    private String zsubconname;
    private String zequnr;
    private String zworker;
    private String mblnr;
    private String mjahr;
    private String zeile;
    private String zcustomer;
    private String zcustomername;
    private String zexfield1;
    private String zexfield2;
    private String zexfield3;
    private String zexfield4;
    private String zexfield5;

}