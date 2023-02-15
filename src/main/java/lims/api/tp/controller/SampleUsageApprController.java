package lims.api.tp.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.tp.service.SampleUsageApprService;
import lims.api.tp.vo.SampleUsageApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/tp/sampleUsageAppr")
public class SampleUsageApprController {
    private final SampleUsageApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<SampleUsageApprVO>> get(@AuthToken Token token, SampleUsageApprVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        request.setAprUid(jwtResolver.getUserId(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody List<SampleUsageApprVO> requests) {
        String plntCd = jwtResolver.getPlantCode(token.getJwt());
        String userId = jwtResolver.getUserId(token.getJwt());
        requests.forEach(request -> {
            request.setPlntCd(plntCd);
            request.setAprUid(userId);

        });
        //service.approve(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @RequestBody List<SampleUsageApprVO> requests) {
        String plntCd = jwtResolver.getPlantCode(token.getJwt());
        String userId = jwtResolver.getUserId(token.getJwt());
        requests.forEach(request -> {
            request.setPlntCd(plntCd);
            request.setAprUid(userId);
            request.setRjtUid(userId);
        });
        service.reject(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

}