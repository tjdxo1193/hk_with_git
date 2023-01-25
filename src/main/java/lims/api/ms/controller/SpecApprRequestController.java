package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.SpecApprRequestService;
import lims.api.ms.vo.SpecApprRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/specApprRequest")
public class SpecApprRequestController {
    private final SpecApprRequestService service;
    private final JwtResolver jwtResolver;

    @GetMapping("/version")
    public ResponseEntity<List<SpecApprRequestVO>> getVersionList(@AuthToken Token token, SpecApprRequestVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setRevwUid(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.getVersionList(param));
    }

    @GetMapping("/aItem")
    public ResponseEntity<List<SpecApprRequestVO>> getAItemList(@AuthToken Token token, SpecApprRequestVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getAItemList(param));
    }

    @PutMapping("/approvalRequest")
    public ResponseEntity<CommonResponse> updateApprovalRequest(@AuthToken Token token, @RequestBody SpecApprRequestVO param) {
        String jwt = token.getJwt();
        String ip = HttpHelper.getClientIp();

        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setRevwIp(ip);

        service.updateApprovalRequest(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reviewReturn")
    public ResponseEntity<CommonResponse> updateReviewReturn(@AuthToken Token token, @RequestBody SpecApprRequestVO param) {
        String jwt = token.getJwt();
        String ip = HttpHelper.getClientIp();

        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setRjtUid(jwtResolver.getUserId(jwt));
        param.setRevwIp(ip);

        service.updateReviewReturn(param);
        return ResponseEntity.ok(new CommonResponse());
    }
}