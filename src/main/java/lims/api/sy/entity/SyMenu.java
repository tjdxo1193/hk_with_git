package lims.api.sy.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "SY_MENU")
public class SyMenu {
    @AuditId
    private String menuCd;
    private String hirMenuCd;
    private String menuNm;
    private int menuOrd;
    private String menuDesc;
    private String menuPrtYn;
    private String useYn;
}