package lims.api.in.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "EM_EQM_INFO")
public class EmEqmInfo {
    @AuditId
    private String plntCd;
    @AuditId
    private String eqmCd;
    private String eqmNm;
    private String eqmDiv;
    private String eqmCrst;
    private String eqmStt;
    private String istPla;
    private String makComp;
    private String splComp;
    private String modNm;
    private String srlNo;
    private String crgUid;
    private String iq;
    private String oq;
    private String pq;
    private String aprNo;
    private String chkCyl;
    private String quaCyl;
    private String calCyl;
    private String etrDt;
    private String istDt;
    private String dpsDt;
    private String qualAprDt;
    private String calAprDt;
    private String rglChkAprDt;
    private String useRng;
    private String qttRng;
    private String rmk;
    private Integer eqmFileIdx;
    private String sapAstNo;
    private String sapAstNm;
    private String sapCrtDt;
    private String sapChgDt;
    private String sapAcqDt;
    private String sapSaleDpsDt;
    private String sapAddDesc;
    private String sapCrgNmEmid;
    private String sapCosc;
    private Integer sapOrco;
    private Integer sapAccd;
    private Character pmsChkTagtYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private Character delYn;
}
