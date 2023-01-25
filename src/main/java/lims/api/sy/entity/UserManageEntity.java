package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_USER")
public class UserManageEntity {
    @AuditId
    private String userId;
    private String plntCd;
    private String dptCd;
    private String athCd;
    private String userNm;
    private String userMail;
    private String userTel;
    private String userLognId;
    private String emid;
    private String gradeNm;
    private String titleNm;
    private String pwd;
    private String useYn;
    private String lockYn;
    private String lognFailCnt;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}