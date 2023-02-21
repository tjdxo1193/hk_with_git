package lims.api.an.controller;

import lims.api.an.service.AnalColSearchService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/an/analColSearch")
public class AnalColSearchController {

    private final AnalColSearchService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<AnalColMaterialVO>> findAll(@AuthToken Token token, AnalColMaterialVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ritmMngIdx}")
    public ResponseEntity<List<AnalColMaterialVO>> findAllByRitmMngIdx(@PathVariable Integer ritmMngIdx) {
        return ResponseEntity.ok(service.findAllByRitmMngIdx(ritmMngIdx));
    }
}