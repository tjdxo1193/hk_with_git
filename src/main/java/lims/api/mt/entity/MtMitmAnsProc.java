package lims.api.mt.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "MT_MITM_ANS_PROC")
public class MtMitmAnsProc {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer mitmReqIdx;
    private Integer mitmSpecIdx;
    private String ansProcCd;
    private String sytJdg;
    private String reqNo;
    private String reqDt;
    private String ansEdt;
    private String reqDs;
    private String reqDptCd;
    private String reqUid;
    private String reqRmk;
    private String reqCanlYn;
    private String reqCanlDs;
    private String reqCanlUid;
    private String reqCanlRea;
    private String ansNo;
    private String rcpDt;
    private String rcpDs;
    private String rcpDptCd;
    private String rcpUid;
    private String rcpRmk;
    private String assNo;
    private String assDt;
    private String assDs;
    private String assSpcc;
    private Integer assAprReqIdx;
    private String cplRqmDt;
    private String wrkSttCd;
    private String pdtPitmCd;
    private String makNo;
    private String revwDt;
    private String revwDs;
    private String revwUid;
    private String revwCmmt;
    private Integer rstAprReqIdx;
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
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;

}
