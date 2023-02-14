package lims.api.st.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.st.service.StabSampleUsageService;
import lims.api.st.vo.StabSampleUsageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/st/stabSampleUsage")
public class StabSampleUsageController {
    private final StabSampleUsageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StabSampleUsageVO>> get(@AuthToken Token token, StabSampleUsageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setAprUid(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.find(request));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@AuthToken Token token, @RequestBody StabSampleUsageVO request) {
        service.create(setUserInfo(token, request));
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody StabSampleUsageVO request) {
        service.update(setUserInfo(token, request));
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @RequestBody StabSampleUsageVO request) {
        service.delete(setUserInfo(token, request));
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestApproveUse")
    public ResponseEntity<CommonResponse> requestApproveUse(@AuthToken Token token, @RequestBody StabSampleUsageVO request) {
        service.requestApproveUse(setUserInfo(token, request));
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestCancelUse")
    public ResponseEntity<CommonResponse> requestCancelUse(@AuthToken Token token, @RequestBody StabSampleUsageVO request) {
        service.requestCancelUse(setUserInfo(token, request));
        return ResponseEntity.ok(new CommonResponse());
    }

    @GetMapping("/sample")
    public ResponseEntity<List<StabSampleUsageVO>> getSample(@AuthToken Token token, StabSampleUsageVO request) {
        return ResponseEntity.ok(service.findSample(setUserInfo(token, request)));
    }

    private StabSampleUsageVO setUserInfo(Token token, StabSampleUsageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setCrtIp(HttpHelper.getClientIp());
        request.setCrtUid(jwtResolver.getUserId(jwt));
        request.setUdtIp(HttpHelper.getClientIp());
        request.setUdtUid(jwtResolver.getUserId(jwt));
        return request;
    }
}