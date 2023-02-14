package lims.api.st.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.st.service.StabSampleUsageApprService;
import lims.api.st.vo.StabSampleUsageApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/st/stabSampleUsageAppr")
public class StabSampleUsageApprController {
    private final StabSampleUsageApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StabSampleUsageApprVO>> get(@AuthToken Token token, StabSampleUsageApprVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        request.setAprUid(jwtResolver.getUserId(token.getJwt()));
        service.find(request);
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody StabSampleUsageApprVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        service.approve(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @RequestBody StabSampleUsageApprVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        request.setRjtUid(jwtResolver.getUserId(token.getJwt()));
        service.reject(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}