package lims.api.kp.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.kp.service.AddSampleApprService;
import lims.api.kp.vo.AddSampleApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/kp/addSampleAppr")
public class AddSampleApprController {
    private final AddSampleApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<AddSampleApprVO>> get(@AuthToken Token token, AddSampleApprVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        request.setAprUid(jwtResolver.getUserId(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody AddSampleApprVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        service.approve(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @RequestBody AddSampleApprVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        request.setRjtUid(jwtResolver.getUserId(token.getJwt()));
        service.reject(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}