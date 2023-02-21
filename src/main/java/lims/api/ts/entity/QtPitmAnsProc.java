package lims.api.ts.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QT_PITM_ANS_PROC")
public class QtPitmAnsProc {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer ansIdx;
    private Integer reqIdx;
    private String pitmSpecIdx;
    private String ansProcCd;
    private String sytJdg;
    private String reqDt;
    private String ansEdt;
    private String ansNo;
    private String rcpDt;
    private String rcpDs;
    private String rcpDptCd;
    private String rcpUid;
    private String rcpRmk;
    private String clltDt;
    private String clltDs;
    private String clltUid;
    private String clltRmk;
    private String assNo;
    private String assDt;
    private String assDs;
    private String assSpcc;
    private Integer assAprReqIdx;
    private String cplRqmDt;
    private String wrkSttCd;
    private String revwDt;
    private String revwDs;
    private String revwUid;
    private String revwCmmt;
    private Integer rstAprReqIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String aprRea;
    private String hldYn;
    private String hldDs;
    private String hldUid;
    private String hldRea;
    private String hldCanlDs;
    private String hldCanlUid;
    private String hldCanlRea;
    private String ansCanlDs;
    private String ansCanlUid;
    private String ansCanlRea;
    private String oosYn;
    private String oosReqUid;
    private String oosReqDs;
    private String oosCd;

}
