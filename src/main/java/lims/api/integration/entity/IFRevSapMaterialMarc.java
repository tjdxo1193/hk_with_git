package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_MATERIAL_MARC")
public class IFRevSapMaterialMarc {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String matnr;
    private String werks;
    private String ladgr;
    private String mmsta;
    private String ekgrp;
    private String insmk;
    private String dismm;
    private String fxhor;
    private String dispo;
    private String disls;
    private String bstrf;
    private String bstmi;
    private String beskz;
    private String sobsl;
    private String lgpro;
    private String lgfsb;
    private String plifz;
    private String webaz;
    private String elslo;
    private String strgr;
    private String mtvfp;
    private String fevor;
    private String kzech;
    private String awsls;
    private String prctr;
    private String losgr;
    private String sobsk;
    private String fvidk;
    private String zfirstGiDate;

}