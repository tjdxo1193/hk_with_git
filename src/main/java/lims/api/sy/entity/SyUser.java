package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_USER")
public class SyUser {
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
    private String graderNm;
    private String titleNm;
    private Character ansYn;
    private Character clltYn;
    private Character revwYn;
    private Character aprYn;
    private String pwd;
    private Character useYn;
    private Character lockYn;
    private Integer lognFailCnt;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private String userNmEn;
    private String userCompTel;
    private Integer ord;
    private Character workStt;
}