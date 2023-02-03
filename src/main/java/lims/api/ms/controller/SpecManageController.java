package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.SpecManageService;
import lims.api.ms.vo.SpecManageAitmVO;
import lims.api.ms.vo.SpecManageDptVO;
import lims.api.ms.vo.SpecManagePitmVO;
import lims.api.ms.vo.SpecManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/specManage")
public class SpecManageController {
    private final SpecManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping("/pItem")
    public ResponseEntity<List<SpecManagePitmVO>> getPItemList(@AuthToken Token token, SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPItemList(param));
    }

    @GetMapping("/version")
    public ResponseEntity<List<SpecManageVO>> getVersionList(@AuthToken Token token, SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getVersionList(param));
    }

    @GetMapping("/aItem")
    public ResponseEntity<List<SpecManageAitmVO>> getAItemList(@AuthToken Token token, SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getAItemList(param));
    }

    @GetMapping("/semiAItem")
    public ResponseEntity<List<SpecManageAitmVO>> getSemiAItemList(@AuthToken Token token, SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSemiAItemList(param));
    }

    @GetMapping("/itemsByTestMethodModal")
    public ResponseEntity<List<SpecManageVO>> getItemMethodList(SpecManageVO param) {
        return ResponseEntity.ok(service.getItemMethodList(param));
    }

    @PostMapping("/updateVersion")
    public ResponseEntity<CommonResponse> updateVersion(@AuthToken Token token, @RequestBody SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.updateVersion(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/aItem")
    public ResponseEntity<CommonResponse> createAItem(@AuthToken Token token, @RequestBody SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.makeAItem(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestReview")
    public ResponseEntity<CommonResponse> updateRequestReview(@AuthToken Token token, @RequestBody SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));

        service.updateRequestReview(param);

        return ResponseEntity.ok(new CommonResponse());
    }

    @GetMapping("/departmentList")
    public ResponseEntity<List<SpecManageDptVO>> getDepartmentList(@AuthToken Token token, SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getDepartmentList(param));
    }

    @GetMapping("/pItemSpecListToModal")
    public ResponseEntity<List<SpecManagePitmVO>> getPItemSpecListToModal(@AuthToken Token token, SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPItemSpecListToModal(param));
    }

    @GetMapping("/aItemListToModal")
    public ResponseEntity<List<SpecManageAitmVO>> getAItemListToModal(@AuthToken Token token, SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getAItemListToModal(param));
    }

    @GetMapping("/getPackagingItemListToModal")
    public ResponseEntity<List<SpecManagePitmVO>> getPackagingItemListToModal(@AuthToken Token token, SpecManageVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPackagingItemListToModal(param));
    }

    @GetMapping("/getSemiPItemListToModal")
    public ResponseEntity<List<SpecManagePitmVO>> getSemiPItemListToModal(@AuthToken Token token, SpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSemiPItemListToModal(param));
    }
}