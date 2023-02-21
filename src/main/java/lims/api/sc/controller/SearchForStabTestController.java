package lims.api.sc.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sc.service.SearchForStabTestService;
import lims.api.sc.vo.SearchForStabTestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sc/searchForStabTest")
public class SearchForStabTestController {
    private final SearchForStabTestService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<SearchForStabTestVO>> get(@AuthToken Token token, SearchForStabTestVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/spec")
    public ResponseEntity<List<SearchForStabTestVO>> getSpec(@AuthToken Token token, SearchForStabTestVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.findSpec(request));
    }

}