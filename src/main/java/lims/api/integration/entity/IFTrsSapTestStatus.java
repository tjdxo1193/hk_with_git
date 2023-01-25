package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_TRS_TEST_STATUS")
public class IFTrsSapTestStatus {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String errorlogId;
    private String guid;
    private String zqcreqno;
    private String zqcstatus;
    private String zexfield1;
    private String zexfield2;
    private String zexfield3;
    private String zexfield4;
    private String zexfield5;
    private String trsStatus;

}