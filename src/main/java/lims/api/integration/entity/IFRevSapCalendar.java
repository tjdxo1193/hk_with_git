package lims.api.integration.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "IF_REV_SAP_CALENDAR")
public class IFRevSapCalendar {

    @AuditId
    private String idx;
    private String degree;
    private String ifInfoIdx;
    private String guid;
    private String seq;
    private String ident;
    private String year;
    private String month;
    private String day;
    private String work;


}