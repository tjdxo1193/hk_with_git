package lims.api.ts.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QE_LAB_EVT")
public class QeLabEvt {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer labEvtIdx;
    private Integer rstSeq;
    private String ansNo;
    private String ansIdx;
    private String pitmTyp;
    private String pitmCd;
    private String pitmNm;
    private String orderNo;
    private String lotNo;
    private String batchNo;
    private String amitmCd;
    private String amitmNm;
    private String ocrDt;
    private String ansUid;
    private String crtvMsr;
    private String tstLogRpubYn;
    private String revwCmmt;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private Integer labEvtAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
}
