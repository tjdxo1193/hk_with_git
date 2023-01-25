package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_TEST_REQUEST_DETAIL")
public class IFRevSAPTestRequestDetails {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String guid;
    private String zexfield4;
    private String zexfield5;
    private String zqcreqno;
    private String zqcnoBlk;
    private String zexfield1;
    private String zexfield2;
    private String zexfield3;

}