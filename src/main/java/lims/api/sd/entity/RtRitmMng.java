package lims.api.sd.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RT_RITM_MNG")
public class RtRitmMng {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer ritmMngIdx;
    private Integer ritmEtrIdx;
    private Integer ritmEtrNo;
    private String mngProcCd;
    private String ritmLabelNo;
    private String opnDt;
    private String expirDt;
    private String opnRmk;
    private String opnUid;
    private String opnDs;
    private String opnCanlUid;
    private String opnCanlDs;
    private String opnCanlRea;
    private Integer dpsReqAprIdx;
    private String dpsRea;
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
