package lims.api.an.controller;

import lims.api.an.service.AnalColReceiptService;
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
@RequestMapping("${apiPrefix}/an/analColReceipt")
public class AnalColReceiptController {
    private final AnalColReceiptService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<AnalColMaterialVO>> findAll(@AuthToken Token token, AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PostMapping
    public ResponseEntity<Integer> create(@AuthToken Token token, @RequestBody AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.create(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        service.update(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        param.setDelUid(getAuthUserId(token));
        param.setDelRea(esign.getReason());
        service.delete(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/savedFile")
    public ResponseEntity<Integer> savedFile(@AuthToken Token token, AnalColMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.savedFile(param));
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