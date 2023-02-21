package lims.api.sd.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.sd.service.StdItemReceiptService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sd/stdItemReceipt")
public class StdItemReceiptController {

    private final StdItemReceiptService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StandardMaterialVO>> findAll(@AuthToken Token token, StandardMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PostMapping
    public ResponseEntity<Integer> create(@AuthToken Token token, @RequestBody StandardMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.create(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody StandardMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        service.update(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody StandardMaterialVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        param.setDelUid(getAuthUserId(token));
        param.setDelRea(esign.getReason());
        service.delete(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestApprove")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody List<StandardMaterialVO> list) {
        for(StandardMaterialVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
        }
        service.requestApprove(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/savedFile")
    public ResponseEntity<Integer> savedFile(@AuthToken Token token, StandardMaterialVO param) {
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