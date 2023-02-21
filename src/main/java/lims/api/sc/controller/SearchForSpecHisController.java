package lims.api.sc.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.ms.vo.SpecManageAitmVO;
import lims.api.ms.vo.SpecManageDptVO;
import lims.api.ms.vo.SpecManagePitmVO;
import lims.api.ms.vo.SpecManageVO;
import lims.api.sc.service.SearchForSpecHisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sc/searchForSpecHis")
public class SearchForSpecHisController {
    private final SearchForSpecHisService service;
    private final JwtResolver jwtResolver;

    @GetMapping("/pItem")
    public ResponseEntity<List<SpecManagePitmVO>> getPItemList(@AuthToken Token token, SpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPItemList(param));
    }

    @GetMapping("/version")
    public ResponseEntity<List<SpecManageVO>> getVersionList(@AuthToken Token token, SpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getVersionList(param));
    }

    @GetMapping("/aItem")
    public ResponseEntity<List<SpecManageAitmVO>> getAItemList(@AuthToken Token token, SpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getAItemList(param));
    }

    @GetMapping("/semiAItem")
    public ResponseEntity<List<SpecManageAitmVO>> getSemiAItemList(@AuthToken Token token, SpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSemiAItemList(param));
    }

    @GetMapping("/departmentList")
    public ResponseEntity<List<SpecManageDptVO>> getDepartmentList(@AuthToken Token token, SpecManageVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getDepartmentList(param));
    }
}