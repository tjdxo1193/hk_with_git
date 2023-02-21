package lims.api.sd.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "RT_RITM_USE")
public class RtRitmUse {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer ritmMngIdx;
    @AuditId
    private Integer useSeq;
    private String useProcCd;
    private String pitmNm;
    private String ansNo;
    private String makNo;
    private String aitmNm;
    private String useUid;
    private String useDt;
    private String useQty;
    private String usePps;
    private String useRmk;
    private String delYn;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
