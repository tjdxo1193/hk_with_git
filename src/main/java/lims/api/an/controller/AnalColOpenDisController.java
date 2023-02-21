package lims.api.an.controller;

import lims.api.an.service.AnalColOpenDisService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/an/analColOpenDis")
public class AnalColOpenDisController {

    private final AnalColOpenDisService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<AnalColMaterialVO>> findAll(@AuthToken Token token, AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PutMapping("/open")
    public ResponseEntity<CommonResponse> open(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        param.setOpnUid(getAuthUserId(token));
        param.setOpnRmk(esign.getReason());
        service.open(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        service.update(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/disposal")
    public ResponseEntity<CommonResponse> disposal(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        param.setAprReqUid(getAuthUserId(token));
        param.setDpsRea(esign.getReason());
        service.disposal(param);
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