package lims.api.sy.controller;

import lims.api.sy.vo.AuditManageMenuVO;
import lims.api.sy.model.AuditManageResponse;
import lims.api.sy.vo.AuditManageTableVO;
import lims.api.sy.service.AuditTrailManageService;
import lims.api.sy.vo.AuditMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 개발자 전용 API Controller 입니다.
 * 기본적으로 운영에는 배포하지 않습니다.
 */

@Profile({ "local", "dev" })
@RestController
@RequestMapping("${apiPrefix}/dev")
@RequiredArgsConstructor
public class AuditTrailManageController {

    private final AuditTrailManageService manageService;

    @GetMapping("audit/menus")
    public ResponseEntity<AuditManageResponse> getMenus(AuditManageMenuVO request) {
        return ResponseEntity.ok(new AuditManageResponse().ofMenus(manageService.getMenus(request)));
    }

    @GetMapping("audit/tables")
    public ResponseEntity<AuditManageResponse> getTables(AuditManageTableVO request) {
        return ResponseEntity.ok(new AuditManageResponse().ofTables(manageService.getTables(request)));
    }

    @GetMapping("audit/tables/{tableName}/columns")
    public ResponseEntity<AuditManageResponse> getTableWithColumns(@PathVariable("tableName") String tableName) {
        return ResponseEntity.ok(new AuditManageResponse().ofColumns(manageService.getColumnsByTableName(tableName)));
    }

    @GetMapping("audit/master/{menuCd}")
    public ResponseEntity<List<AuditMaster>> getAuditMaster(@PathVariable("menuCd") String menuCd) {
        return ResponseEntity.ok(manageService.getAuditMaster(menuCd));
    }

    @PostMapping("audit/master/{menuCd}")
    public ResponseEntity<List<AuditMaster>> saveAuditMaster(@PathVariable("menuCd") String menuCd, @RequestBody List<AuditMaster> param) {
        return ResponseEntity.ok(manageService.saveAuditMaster(menuCd, param));
    }

}