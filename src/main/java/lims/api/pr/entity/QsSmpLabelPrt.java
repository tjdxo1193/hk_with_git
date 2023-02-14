package lims.api.pr.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QS_SMP_LABEL_PRT")
public class QsSmpLabelPrt {
    @AuditId
    private Integer labelPrtIdx;
    @AuditId
    private Integer prtSeq;
    private Integer labelRptIdx;
    private String labelPrtProc;
    private Integer labelRePrtAprIdx;
    private String labelRePrtRjtUid;
    private String labelRePrtRjtDs;
    private String labelRePrtRjtRea;
    private Integer prtUid;
    private String prtDs;

}
