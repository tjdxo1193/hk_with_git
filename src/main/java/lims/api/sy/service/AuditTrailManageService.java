package lims.api.sy.service;

import lims.api.sy.vo.AuditManageMenuVO;
import lims.api.sy.vo.AuditManageTableVO;
import lims.api.sy.vo.AuditManageColumn;
import lims.api.sy.vo.AuditManageTable;
import lims.api.sy.vo.AuditMaster;
import lims.api.sy.vo.AuditMenu;

import java.util.List;

public interface AuditTrailManageService {

    List<AuditMenu> getMenus(AuditManageMenuVO dto);

    List<AuditManageTable> getTables(AuditManageTableVO dto);

    List<AuditManageColumn> getColumnsByTableName(String tableName);

    List<AuditMaster> getAuditMaster(String menuCd);

    List<AuditMaster> saveAuditMaster(String menuCd, List<AuditMaster> masters);

}