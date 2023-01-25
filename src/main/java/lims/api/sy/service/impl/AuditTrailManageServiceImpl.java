package lims.api.sy.service.impl;

import lims.api.sy.dao.AuditTrailManageDao;
import lims.api.sy.vo.AuditManageMenuVO;
import lims.api.sy.vo.AuditManageTableVO;
import lims.api.sy.service.AuditTrailManageService;
import lims.api.sy.vo.AuditManageColumn;
import lims.api.sy.vo.AuditManageTable;
import lims.api.sy.vo.AuditMaster;
import lims.api.sy.vo.AuditMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditTrailManageServiceImpl implements AuditTrailManageService {

    @Value("${spring.datasource.username}")
    private String owner;

    private final AuditTrailManageDao manageDao;

    @Override
    public List<AuditMenu> getMenus(AuditManageMenuVO dto) {
        return manageDao.findMenuAll(dto);
    }

    @Override
    public List<AuditManageTable> getTables(AuditManageTableVO dto) {
        dto.setOwner(owner);
        return manageDao.findTableAll(dto);
    }

    @Override
    public List<AuditManageColumn> getColumnsByTableName(String tableName) {
        AuditManageTableVO dto = new AuditManageTableVO();
        dto.setOwner(owner);
        dto.setTableNm(tableName);
        return manageDao.findColumnsByTableName(dto);
    }

    @Override
    public List<AuditMaster> getAuditMaster(String menuCd) {
        return manageDao.findAuditMasterByMenuCode(menuCd);
    }

    @Override
    public List<AuditMaster> saveAuditMaster(String menuCd, List<AuditMaster> masters) {
        manageDao.deleteAuditMasterByMenuCode(menuCd);

        for (AuditMaster master : masters ) {
            int seqno = manageDao.nextAuditMasterKey(menuCd);
            master.setSeqno(seqno);
            manageDao.createAuditMaster(master);
        }
        return masters;
    }

}