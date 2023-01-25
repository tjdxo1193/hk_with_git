package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QM_PKGA")
public class QmPkga {
    @AuditId
    private String plntCd;
    @AuditId
    private String pkgaCd;
    @AuditId
    private String pkgaVer;
    private String aitmSpecIdx;
    private String pkgaDiv;
    private String pkgaTypNm;
    private String sapPrdha;
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
    private String pkgaSpecAprIdx;
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
