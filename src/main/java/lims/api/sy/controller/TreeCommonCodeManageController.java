package lims.api.sy.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sy.service.TreeCommonCodeManageService;
import lims.api.sy.vo.TreeCommonCodeManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sy/treeCommonCodeManage")
@RequiredArgsConstructor
public class TreeCommonCodeManageController {

    private final TreeCommonCodeManageService commonCodeManageService;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TreeCommonCodeManageVO>> getCodes(@AuthToken Token token) {
        return ResponseEntity.ok(commonCodeManageService.getCodes(jwtResolver.getPlantCode(token.getJwt())));
    }

    @GetMapping("/{code}")
    public ResponseEntity<List<TreeCommonCodeManageVO>> getCodesByCode(@AuthToken Token token, @PathVariable("code") String treeCd, TreeCommonCodeManageVO param) {
        return ResponseEntity.ok(commonCodeManageService.getCodesByCode(jwtResolver.getPlantCode(token.getJwt()), treeCd, param));
    }

    @PostMapping("/node")
    public ResponseEntity<CommonResponse> createNode(@AuthToken Token token, @RequestBody TreeCommonCodeManageVO requestParam) {
        commonCodeManageService.saveNode(jwtResolver.getPlantCode(token.getJwt()), requestParam.getAddedList(), requestParam.getUpdatedList());
        return ResponseEntity.ok(new CommonResponse());
    }

}