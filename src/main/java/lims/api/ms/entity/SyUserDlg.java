package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_USER_DLG")
public class SyUserDlg {
    @AuditId
    private String userId;
    @AuditId
    private String dlgSeq;
    private String dlgUid;
    private String dlgDt;
    private String gbkDt;
    private String dlgRea;
    private String dlgProcCd;
    private String dlgAprReqIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String dlgSttCd;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
}
