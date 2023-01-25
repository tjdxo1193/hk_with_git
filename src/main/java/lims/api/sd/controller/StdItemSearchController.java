package lims.api.sd.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sd.service.StdItemSearchService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sd/stdItemSearch")
public class StdItemSearchController {

    private final StdItemSearchService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StandardMaterialVO>> findAll(@AuthToken Token token, StandardMaterialVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ritmEtrIdx}")
    public ResponseEntity<List<StandardMaterialVO>> findAllByRitmEtrIdx(@PathVariable Integer ritmEtrIdx) {
        return ResponseEntity.ok(service.findAllByRitmEtrIdx(ritmEtrIdx));
    }

    @GetMapping("/{ritmEtrIdx}/{ritmMngIdx}")
    public ResponseEntity<List<StandardMaterialVO>> findAllByRitmMngIdx(@PathVariable Integer ritmEtrIdx, @PathVariable Integer ritmMngIdx, @RequestParam(required = false) String delYn) {
        return ResponseEntity.ok(service.findAllByRitmMngIdx(ritmEtrIdx, ritmMngIdx, delYn));
    }
}