package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "MM_MITM_SPEC")
public class MmMitmSpec {
    @AuditId
    private String plntCd;
    @AuditId
    private String mitmSpecIdx;
    private String mitmCd;
    private String aitmSpecIdx;
    private String specProcCd;
    private String delYn;
    private String useVerYn;
    private String rvsNo;
    private String docNo;
    private String rvsDt;
    private String enfoDt;
    private String rvsReaCd;
    private String rvsCtt;
    private String rvsDivPs;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private String mitmSpecAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
