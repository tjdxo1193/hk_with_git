package lims.api.common.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_APR_INFO")
public class SyAprInfo {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer aprIdx;
    private String aprReqDiv;
    private String aprReqUid;
    private String aprReqDs;
    private String aprReqIp;
    private String aprReqRea;
    private String aprUid;
    private String aprDs;
    private String aprIp;
    private String aprRea;
    private String aprYn;
}
