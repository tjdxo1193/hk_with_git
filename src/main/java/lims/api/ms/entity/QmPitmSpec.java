package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QM_PITM_SPEC")
public class QmPitmSpec {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer pitmSpecIdx;
    private String pitmCd;
    private Integer pitmVer;
    private Integer aitmSpecIdx;
    private String specProcCd;
    private Character delYn;
    private Character useVerYn;
    private String rvsNo;
    private String docNo;
    private String rvsDt;
    private String enfoDt;
    private String rvsReaCd;
    private String rvsCtt;
    private Character rvsDivPs;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private Integer pitmAnsSpecAprIdx;
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
