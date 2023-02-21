package lims.api.sd.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sd.service.StdItemOpenDisService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sd/stdItemOpenDis")
public class StdItemOpenDisController {

    private final StdItemOpenDisService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StandardMaterialVO>> findAll(@AuthToken Token token, StandardMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PutMapping("/open")
    public ResponseEntity<CommonResponse> open(@AuthToken Token token, @RequestBody List<StandardMaterialVO> list) {
        for(StandardMaterialVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setOpnUid(getAuthUserId(token));
        }
        service.open(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestDisposal")
    public ResponseEntity<CommonResponse> requestDisposal(@AuthToken Token token, @RequestBody List<StandardMaterialVO> list) {
        for(StandardMaterialVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
        }
        service.requestDisposal(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    private String getAuthUserPlntCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}