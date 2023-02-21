package lims.api.st.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.st.service.StabSampleDisService;
import lims.api.st.vo.StabSampleDisVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/st/stabSampleDis")
public class StabSampleDisController {
    private final StabSampleDisService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StabSampleDisVO>> get(@AuthToken Token token, StabSampleDisVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping("/requestDispose")
    public ResponseEntity<CommonResponse> requestDispose(@AuthToken Token token, @RequestBody List<StabSampleDisVO> requests) {
        requests.forEach(request -> setApproveInfo(token, request));
        service.requestDispose(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestCancelDispose")
    public ResponseEntity<CommonResponse> requestCancelDispose(@AuthToken Token token, @RequestBody List<StabSampleDisVO> requests) {
        requests.forEach(request -> setApproveInfo(token, request));
        service.requestCancelDispose(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    private StabSampleDisVO setApproveInfo(Token token, StabSampleDisVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setAprReqIp(HttpHelper.getClientIp());
        return param;
    }
}