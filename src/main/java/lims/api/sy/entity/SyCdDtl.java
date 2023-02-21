package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_CD_DTL")
public class SyCdDtl {
    @AuditId
    private String plntCd;
    @AuditId
    private String hirCd;
    @AuditId
    private String dtlCd;
    private String dtlNm;
    private String dtlEn;
    private String dtlAbbr;
    private String dtlCdOrd;
    private String useYn;
    private String dtlRmk;
    private String extCd1;
    private String extCd2;
    private String extCd3;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}