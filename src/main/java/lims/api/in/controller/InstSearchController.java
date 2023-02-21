package lims.api.in.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.in.service.InstSearchService;
import lims.api.in.vo.InstSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/in/instSearch")
public class InstSearchController {
    private final InstSearchService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<InstSearchVO>> get(@AuthToken Token token, InstSearchVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<InstSearchVO>> getHistory(@AuthToken Token token, InstSearchVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findHistory(request));
    }
}