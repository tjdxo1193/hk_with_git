package lims.api.st.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "ST_SBT_ANS")
public class StSbtAns {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer sbtAnsIdx;
    private Integer sbtPlnIdx;
    private String sbtAnsStt;
    private String ansDelYn;
    private String ansDt;
    private String accMarkNm;
    private String ansSmpVol;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
