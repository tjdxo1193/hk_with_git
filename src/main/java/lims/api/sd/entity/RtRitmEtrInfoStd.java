package lims.api.sd.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RT_RITM_ETR_INFO_STD")
public class RtRitmEtrInfoStd {
    @AuditId
    private String plntCd;
    @AuditId
    private String ritmEtrIdx;
    private String stdMois;
    private String stdNb;
    private String stdCtetCd;
    private String stdCtetVal;
    private String stdCtetUnitCd;
    private String stdAsisCtetNm;
    private String stdAsisCtetVal;
    private String stdAsisCtetUnitCd;
    private String stdRmtrMakNo;
    private String stdRmtrAnsNo;
}
