package lims.api.re.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.re.service.ReagentOpenDisService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/re/reagentOpenDis")
public class ReagentOpenDisController {

    private final ReagentOpenDisService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<ReagentMaterialVO>> findAll(@AuthToken Token token, ReagentMaterialVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PutMapping("/open")
    public ResponseEntity<CommonResponse> open(@AuthToken Token token, @RequestBody List<ReagentMaterialVO> list) {
        String jwt = token.getJwt();
        for(ReagentMaterialVO row : list) {
            row.setPlntCd(jwtResolver.getPlantCode(jwt));
            row.setOpnUid(jwtResolver.getUserId(jwt));
        }
        service.open(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestDisposal")
    public ResponseEntity<CommonResponse> requestDisposal(@AuthToken Token token, @RequestBody List<ReagentMaterialVO> list) {
        String jwt = token.getJwt();
        for(ReagentMaterialVO row : list) {
            row.setPlntCd(jwtResolver.getPlantCode(jwt));
        }
        service.requestDisposal(list);
        return ResponseEntity.ok(new CommonResponse());
    }
}