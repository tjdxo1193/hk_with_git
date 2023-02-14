package lims.api.sc.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QT_PITM_ANS_PDT_PFA")
public class QtPitmAnsPdtPfa {
    @AuditId
    private Integer ispPdtPfaIdx;
    @AuditId
    private Integer srlNo;
    private String ispPfaCanlYn;
    private String ifMtdDiv;
    private String ifStt;
    private String hoprIfUid;
    private String ifDs;
    private String ifInfoIdx;
    private String degree;
}
