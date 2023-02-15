package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.common.vo.ComboVO;
import lims.api.ms.service.MonitorSpecManageService;
import lims.api.ms.vo.MonitorSpecManageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/monitorSpecManage")
public class MonitorSpecManageController {
    private final MonitorSpecManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping("/mSpec")
    public ResponseEntity<List<MonitorSpecManageVO>> getMItemSpecList(@AuthToken Token token, MonitorSpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));

        return ResponseEntity.ok(service.getMItemSpecList(param));
    }

    @GetMapping("/version")
    public ResponseEntity<List<MonitorSpecManageVO>> getVersionList(@AuthToken Token token, MonitorSpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getVersionList(param));
    }

    @GetMapping("/aItem")
    public ResponseEntity<List<MonitorSpecManageVO>> getMItemSpecAItemList(@AuthToken Token token, MonitorSpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMItemSpecAItemList(param));
    }

    @PostMapping("/newVersion")
    public ResponseEntity<CommonResponse> createNewVersion(@AuthToken Token token, @RequestBody MonitorSpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.createNewVersion(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/firstVersion")
    public ResponseEntity<CommonResponse> createFirstVersion(@AuthToken Token token, @RequestBody MonitorSpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.createFirstVersion(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/aItem")
    public ResponseEntity<CommonResponse> makeAItem(@AuthToken Token token, @RequestBody MonitorSpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        log.info("[bug report] create A item. rows count: {}, edited rows count: {}.", param.getAddedRowItems().size(), param.getEditedRowItems().size());
        service.makeAItem(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestReview")
    public ResponseEntity<CommonResponse> updateRequestReview(@AuthToken Token token, @RequestBody MonitorSpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.updateRequestReview(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @GetMapping("/itemsByTestMethodModal")
    public ResponseEntity<List<MonitorSpecManageVO>> getItemMethodList(MonitorSpecManageVO param){
        return ResponseEntity.ok(service.getItemMethodList(param));
    }

    @GetMapping("/departmentList")
    public ResponseEntity<List<ComboVO>> getDepartmentList(@AuthToken Token token, MonitorSpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getDepartmentList(param));
    }
}
