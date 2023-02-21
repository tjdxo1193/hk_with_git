package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "MM_MITM")
public class MmMitm {
    @AuditId
    private String plntCd;
    @AuditId
    private String mitmCd;
    private String mitmWrkPlcDiv;
    private String mitmPitmDiv;
    private String point;
    private String roomno;
    private String grade;
    private String ansStrDt;
    private String ansCylCd;
    private String crgDptCd;
    private String perSpec;
    private String revwDurTm;
    private String aprDurTm;
    private Character useYn;
    private Character delYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
