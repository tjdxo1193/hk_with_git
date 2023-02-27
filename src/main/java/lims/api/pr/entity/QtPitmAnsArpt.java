package lims.api.pr.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

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
    private String wrtUid;
    private String wrtDs;
    private String prtUid;
    private String prtDs;
    private Character delYn;
}
