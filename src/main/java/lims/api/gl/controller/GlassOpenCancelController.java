package lims.api.gl.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.gl.service.GlassOpenCancelService;
import lims.api.gl.vo.GlassMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/gl/glassOpenCancel")
public class GlassOpenCancelController {

    private final GlassOpenCancelService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<GlassMaterialVO>> findAll(@AuthToken Token token, GlassMaterialVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> cancel(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<GlassMaterialVO> list) {
        String jwt = token.getJwt();
        for(GlassMaterialVO row : list) {
            row.setPlntCd(jwtResolver.getPlantCode(jwt));
            row.setOpnCanlUid(jwtResolver.getUserId(jwt));
            row.setOpnCanlRea(esign.getReason());
        }

        service.cancel(list);
        return ResponseEntity.ok(new CommonResponse());
    }
}