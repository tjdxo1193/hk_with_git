package lims.api.sy.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sy.service.PwdChangeService;
import lims.api.sy.vo.PwdChangeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sy/pwdChange")
public class PwdChangeController {
    private final PwdChangeService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<PwdChangeVO> getUserInfo(@AuthToken Token token){
        String jwt = token.getJwt();
        PwdChangeVO param = new PwdChangeVO();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setUserId(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.getUserInfo(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> pwdChange(@AuthToken Token token, @RequestBody PwdChangeVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setUserId(jwtResolver.getUserId(jwt));
        service.pwdChange(param);
        return ResponseEntity.ok(new CommonResponse());
    }
}