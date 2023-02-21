package lims.api.in.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.in.service.InstHisApprService;
import lims.api.in.vo.InstHisApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/in/instHisAppr")
public class InstHisApprController {

    private final InstHisApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<InstHisApprVO>> findAll(@AuthToken Token token, InstHisApprVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        param.setAprUid(getAuthUserId((token)));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{hisSeq}")
    public ResponseEntity<List<InstHisApprVO>> findByHisSeq(@PathVariable Integer hisSeq) {
        return ResponseEntity.ok(service.findByHisSeq(hisSeq));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody List<InstHisApprVO> list) {
        for(InstHisApprVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
        }
        service.approve(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<InstHisApprVO> list) {
        for(InstHisApprVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setRjtUid(getAuthUserId(token));
            row.setRjtRea(esign.getReason());
        }
        service.reject(list);
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