package lims.api.sy.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sy.service.LoginHistoryService;
import lims.api.sy.vo.LoginHistoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sy/loginHistory")
public class LoginHistoryController {
    private final LoginHistoryService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<LoginHistoryVO>> get(@AuthToken Token token, LoginHistoryVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }
}
