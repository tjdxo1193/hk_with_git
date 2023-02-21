package lims.api.ts.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QT_PITM_ANS_INFO")
public class QtPitmAnsInfo {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer ansIdx;
    private String crgDptCd;
    private String spcmNo;
    private Integer ansFileIdx;
    private String clltMtd;
    private String clltMcn;
    private String clltPla;
    private String smpStrgMtd;
    private String smpVolUnit;
    private String smpVolAns;
    private String smpVolEtc;
    private String smpVolAdd;
    private String smpVolMng;
    private String ansNewYn;
    private String micYn;
    private String rtstYn;
    private Integer ansOrgReqIdx;
    private String emgYn;
    private Integer arptRptIdx;
    private String pkgMtrSpec;
    private String rmtrSpec;
    private String rmtrCfmul;
    private String rptSpcc;
    private String arptSpcc;
}
