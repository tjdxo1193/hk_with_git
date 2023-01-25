package lims.api.re.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.re.service.ReagentSearchService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/re/reagentSearch")
public class ReagentSearchController {

    private final ReagentSearchService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<ReagentMaterialVO>> findAll(@AuthToken Token token, ReagentMaterialVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ritmEtrIdx}")
    public ResponseEntity<List<ReagentMaterialVO>> findAllByRitmEtrIdx(@PathVariable Integer ritmEtrIdx) {
        return ResponseEntity.ok(service.findAllByRitmEtrIdx(ritmEtrIdx));
    }

    @GetMapping("/{ritmEtrIdx}/{ritmMngIdx}")
    public ResponseEntity<List<ReagentMaterialVO>> findAllByRitmMngIdx(@PathVariable Integer ritmEtrIdx, @PathVariable Integer ritmMngIdx, @RequestParam(required = false) String delYn) {
        return ResponseEntity.ok(service.findAllByRitmMngIdx(ritmEtrIdx, ritmMngIdx, delYn));
    }
}