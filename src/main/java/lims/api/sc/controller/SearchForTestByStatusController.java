package lims.api.sc.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sc.service.SearchForTestByStatusService;
import lims.api.sc.vo.SearchForTestByStatusVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sc/searchForTestByStatus")
public class SearchForTestByStatusController {
    private final SearchForTestByStatusService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<SearchForTestByStatusVO>> get(@AuthToken Token token, SearchForTestByStatusVO request) {
        request.setPlntCd(getAuthUserPlantCode(token));
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/spec")
    public ResponseEntity<List<SearchForTestByStatusVO>> getSpec(@AuthToken Token token, SearchForTestByStatusVO request) {
        request.setPlntCd(getAuthUserPlantCode(token));
        return ResponseEntity.ok(service.findSpec(request));
    }

    private String getAuthUserPlantCode(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }
}