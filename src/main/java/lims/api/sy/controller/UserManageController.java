package lims.api.sy.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sy.service.UserManageService;
import lims.api.sy.vo.UserManageVO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sy/userManage")
public class UserManageController {

    private final UserManageService service;

    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<UserManageVO>> findAll(@AuthToken Token token, UserManageVO vo) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        vo.setPlntCd(plntCd);
        return ResponseEntity.ok(service.findAll(vo));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody UserManageVO vo) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        vo.setPlntCd(plntCd);
        if(Strings.isNotEmpty(vo.getUserId())){
            service.update(vo);
        }
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("initPwd")
    public ResponseEntity<CommonResponse> initPwd(@AuthToken Token token, @RequestBody UserManageVO vo) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        vo.setPlntCd(plntCd);
        if(Strings.isNotEmpty(vo.getUserId())){
            service.initPwd(vo);
        }
        return ResponseEntity.ok(new CommonResponse());
    }
}