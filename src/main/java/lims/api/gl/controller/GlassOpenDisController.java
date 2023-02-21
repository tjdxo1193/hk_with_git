package lims.api.gl.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.gl.service.GlassOpenDisService;
import lims.api.gl.vo.GlassMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/gl/glassOpenDis")
public class GlassOpenDisController {

    private final GlassOpenDisService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<GlassMaterialVO>> findAll(@AuthToken Token token, GlassMaterialVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PutMapping("/open")
    public ResponseEntity<CommonResponse> open(@AuthToken Token token, @RequestBody List<GlassMaterialVO> list) {
        String jwt = token.getJwt();
        for(GlassMaterialVO row : list) {
            row.setPlntCd(jwtResolver.getPlantCode(jwt));
            row.setOpnUid(jwtResolver.getUserId(jwt));
        }
        service.open(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/disposal")
    public ResponseEntity<CommonResponse> disposal(@AuthToken Token token, @RequestBody List<GlassMaterialVO> list) {
        String jwt = token.getJwt();
        for(GlassMaterialVO row : list) {
            row.setPlntCd(jwtResolver.getPlantCode(jwt));
            row.setAprReqUid(jwtResolver.getUserId(jwt));
        }
        service.disposal(list);
        return ResponseEntity.ok(new CommonResponse());
    }
}