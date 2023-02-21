package lims.api.sy.model;

import lims.api.sy.vo.AuditManageColumn;
import lims.api.sy.vo.AuditManageTable;
import lims.api.sy.vo.AuditMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuditManageResponse {

    private List<AuditMenu> menus;
    private List<AuditManageTable> tables;
    private List<AuditManageColumn> columns;

    public AuditManageResponse() {}

    public AuditManageResponse ofMenus(List<AuditMenu> menus) {
        AuditManageResponse response = new AuditManageResponse();
        response.setMenus(menus);
        return response;
    }

    public AuditManageResponse ofTables(List<AuditManageTable> tables) {
        AuditManageResponse response = new AuditManageResponse();
        response.setTables(tables);
        return response;
    }

    public AuditManageResponse ofColumns(List<AuditManageColumn> columns) {
        AuditManageResponse response = new AuditManageResponse();
        response.setColumns(columns);
        return response;
    }

}