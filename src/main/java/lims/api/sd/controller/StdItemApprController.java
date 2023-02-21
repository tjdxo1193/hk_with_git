package lims.api.sd.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.sd.service.StdItemApprService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sd/stdItemAppr")
public class StdItemApprController {

    private final StdItemApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StandardMaterialVO>> findAll(@AuthToken Token token, StandardMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        param.setAprUid(getAuthUserId(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody  List<StandardMaterialVO> list) {
        for(StandardMaterialVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
        }
        service.approve(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<StandardMaterialVO> list) {
        for(StandardMaterialVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setRjtRea(esign.getReason());
        }
        service.reject(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    private String getAuthUserPlntCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}