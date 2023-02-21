package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_TRS_SAP_TEST_PERFORM_PCH_IN")
public class IFTrsSapTestPerformance {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String errorlogId;
    private String trsStatus;
    private String kokrs;
    private String gjahr;
    private String poper;
    private String werks;
    private String matnr;
    private String ebeln;
    private String zpoQty;
    private String zqcQty;

}