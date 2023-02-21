package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QM_PITM_INFO")
public class QmPitmInfo {
    @AuditId
    private String plntCd;
    @AuditId
    private String pitmCd;
    @AuditId
    private Integer pitmVer;
    private String pitmTyp;
    private String pitmNm;
    private String pitmEn;
    private String sapPrdha;
    private Integer ansDurDay;
    private String crgDptCd;
    private String spcmNo;
    private Character micYn;
    private String clltMtd;
    private String clltMcn;
    private String clltPla;
    private String smpStrgMtd;
    private String smpVolUnit;
    private Integer smpVolAns;
    private Integer smpVolEtc;
    private String pkgMtrSpec;
    private String smpVolStrg;
}
