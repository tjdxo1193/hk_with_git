package lims.api.lb.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.lb.service.LabEventSearchService;
import lims.api.lb.vo.LabEventSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/lb/labEventSearch")
public class LabEventSearchController {

    private final LabEventSearchService labEventSearchService;

    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<LabEventSearchVO>> findAll(@AuthToken Token token, LabEventSearchVO dto) {
        dto.setPlntCd(this.getAuthUserPlntCd(token));
        dto.setLoginUserUid(this.getAuthUserId(token));
        return ResponseEntity.ok(labEventSearchService.findAll(dto));
    }

    // getByToken methods
    private String getAuthUserPlntCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}