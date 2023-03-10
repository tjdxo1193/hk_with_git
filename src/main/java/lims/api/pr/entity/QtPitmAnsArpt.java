package lims.api.pr.entity;

import lims.api.sy.entity.SyUser;
import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;
import spring.audit.annotation.AuditJoin;

@AuditEntity(name = "QT_PITM_ANS_ARPT")
public class QtPitmAnsArpt {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer ansIdx;
    @AuditId
    private Integer arptSeq;
    private Integer arptRptIdx;
    private String arptSpcc;
    private String shiptDt;
    private Integer shiptQty;
    @AuditJoin(entity = SyUser.class, definition = "nameById")
    private String wrtUid;
    private String wrtDs;
    @AuditJoin(entity = SyUser.class, definition = "nameById")
    private String prtUid;
    private String prtDs;
    private Character delYn;
}
