package lims.api.in.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "EM_EQM_HIS")
public class EmEqmHis {
    @AuditId
    private String plntCd;
    @AuditId
    private String eqmCd;
    @AuditId
    private Integer hisSeq;
    private String eqmHisDiv;
    private String eqmHisProcCd;
    private Integer eqmHisAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private Character delYn;
    private String rgtUid;
    private String rgtDt;
    private String ansDt;
    private String rmk;
    private Integer hisFileIdx;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
