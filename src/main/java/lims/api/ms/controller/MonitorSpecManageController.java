package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.common.vo.ComboVO;
import lims.api.ms.service.MonitorSpecManageService;
import lims.api.ms.vo.MonitorSpecManageVO;
import lims.api.ms.vo.SpecManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/version")
    public ResponseEntity<CommonResponse> createVersion(@AuthToken Token token, @RequestBody List<MonitorSpecManageVO> param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        if(!param.isEmpty()) {
            param.forEach(element -> element.setPlntCd(plntCd));
            service.createVersion(param);
        }
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/aItem")
    public ResponseEntity<CommonResponse> createMItemSpecAItem(@AuthToken Token token, @RequestBody List<MonitorSpecManageVO> param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);

        if(!param.isEmpty()) {
            param.forEach(element -> element.setPlntCd(plntCd));
            service.createMItemSpecAItem(param);
        }
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestReview")
    public ResponseEntity<CommonResponse> updateRequestReview(@AuthToken Token token, @RequestBody MonitorSpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setRevwUid(param.getAprUid());
        service.updateRequestReview(param);
        return ResponseEntity.ok(new CommonResponse());
    }
    // delete

    @PutMapping("/mSpec/delete")
    public ResponseEntity<CommonResponse> deleteMItemSpec(@AuthToken Token token, @RequestBody MonitorSpecManageVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        service.deleteMItemSpec(param);
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
