package lims.api.sy.dao;

import lims.api.sy.vo.AuditManageMenuVO;
import lims.api.sy.vo.AuditManageTableVO;
import lims.api.sy.vo.AuditManageColumn;
import lims.api.sy.vo.AuditManageTable;
import lims.api.sy.vo.AuditMaster;
import lims.api.sy.vo.AuditMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuditTrailManageDao {

    List<AuditMenu> findMenuAll(AuditManageMenuVO dto);

    List<AuditManageTable> findTableAll(AuditManageTableVO dto);

    List<AuditManageColumn> findColumnsByTableName(AuditManageTableVO dto);

    List<AuditMaster> findAuditMasterByMenuCode(String menuCd);

    int nextAuditMasterKey(String menuCd);

    int createAuditMaster(AuditMaster param);

    int deleteAuditMasterByMenuCode(String menuCd);

}