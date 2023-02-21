package lims.api.in.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "EM_EQM_USE")
public class EmEqmUse {
    @AuditId
    private String plntCd;
    @AuditId
    private String eqmCd;
    @AuditId
    private Integer useSeq;
    private String useStrDs;
    private String useEndDs;
    private String pitmNm;
    private String ansNo;
    private String batchNo;
    private String aitmNm;
    private String ansUid;
    private String rmk;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private Character delYn;
}
