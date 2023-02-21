package lims.api.gl.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.gl.service.GlassSearchService;
import lims.api.gl.vo.GlassMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/gl/glassSearch")
public class GlassSearchController {

    private final GlassSearchService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<GlassMaterialVO>> findAll(@AuthToken Token token, GlassMaterialVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ritmEtrIdx}")
    public ResponseEntity<List<GlassMaterialVO>> findAllByRitmEtrIdx(@PathVariable Integer ritmEtrIdx) {
        return ResponseEntity.ok(service.findAllByRitmEtrIdx(ritmEtrIdx));
    }
}