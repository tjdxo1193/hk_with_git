package lims.api.tp.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.tp.service.SampleDisApprService;
import lims.api.tp.vo.SampleDisApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/tp/sampleDisAppr")
public class SampleDisApprController {
    private final SampleDisApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<SampleDisApprVO>> get(@AuthToken Token token, SampleDisApprVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        request.setAprUid(jwtResolver.getUserId(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping("approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody List<SampleDisApprVO> requests) {
        setUserInfo(token, requests);
        service.approve(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @RequestBody List<SampleDisApprVO> requests) {
        setUserInfo(token, requests);
        service.reject(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    private void setUserInfo(@AuthToken Token token, @RequestBody List<SampleDisApprVO> requests) {
        String plntCd = jwtResolver.getPlantCode(token.getJwt());
        String userId = jwtResolver.getUserId(token.getJwt());
        requests.forEach(request -> {
            request.setPlntCd(plntCd);
            request.setAprUid(userId);
        });
    }
}