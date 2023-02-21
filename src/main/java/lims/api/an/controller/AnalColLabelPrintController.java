package lims.api.an.controller;

import lims.api.an.service.AnalColLabelPrintService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/an/analColLabelPrint")
public class AnalColLabelPrintController {

    private final AnalColLabelPrintService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<AnalColMaterialVO>> findAll(@AuthToken Token token, AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        service.update(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    private String getAuthUserPlntCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }
}