package lims.api.tp.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.tp.service.SampleDisService;
import lims.api.tp.vo.SampleDisVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/tp/sampleDis")
public class SampleDisController {
    private final SampleDisService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<SampleDisVO>> get(@AuthToken Token token, SampleDisVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping("/requestDis")
    public ResponseEntity<CommonResponse> requestDisposal(@AuthToken Token token, @RequestBody List<SampleDisVO> requests) {
        String plntCd = jwtResolver.getPlantCode(token.getJwt());
        String userId = jwtResolver.getUserId(token.getJwt());
        requests.forEach(request -> {
            request.setPlntCd(plntCd);
            request.setAprReqUid(userId);
        });
        service.requestDisposal(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestCancelDis")
    public ResponseEntity<CommonResponse> requestDisposalCancel(@AuthToken Token token, @RequestBody List<SampleDisVO> requests) {
        String plntCd = jwtResolver.getPlantCode(token.getJwt());
        String userId = jwtResolver.getUserId(token.getJwt());
        requests.forEach(request -> {
            request.setPlntCd(plntCd);
            request.setAprReqUid(userId);
        });
        service.requestDisposalCancel(requests);
        return ResponseEntity.ok(new CommonResponse());
    }
}