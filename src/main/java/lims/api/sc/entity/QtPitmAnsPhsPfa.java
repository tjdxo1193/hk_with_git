package lims.api.sc.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QT_PITM_ANS_PHS_PFA")
public class QtPitmAnsPhsPfa {
    @AuditId
    private Integer ispPhsPfaIdx;
    @AuditId
    private Integer srlNo;
    private String ifMtdDiv;
    private String ifStt;
    private String hoprIfUid;
    private String ifDs;
    private String ifInfoIdx;
    private String degree;
}
