package lims.api.st.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.st.service.StabSampleDisApprService;
import lims.api.st.vo.StabSampleDisApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/st/stabSampleDisAppr")
public class StabSampleDisApprController {
    private final StabSampleDisApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StabSampleDisApprVO>> get(@AuthToken Token token, StabSampleDisApprVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setAprUid(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody List<StabSampleDisApprVO> requests) {
        setUserInfo(token, requests);
        service.approve(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @RequestBody List<StabSampleDisApprVO> requests) {
        setUserInfo(token, requests);
        service.reject(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    private void setUserInfo(@AuthToken Token token, @RequestBody List<StabSampleDisApprVO> requests) {
        String plntCd = jwtResolver.getPlantCode(token.getJwt());
        String userId = jwtResolver.getUserId(token.getJwt());
        requests.forEach(request -> {
            request.setPlntCd(plntCd);
            request.setAprUid(userId);
        });
    }
}