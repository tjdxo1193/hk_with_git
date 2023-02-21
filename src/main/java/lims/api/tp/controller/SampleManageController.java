package lims.api.tp.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.tp.service.SampleManageService;
import lims.api.tp.vo.SampleManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/tp/sampleManage")
public class SampleManageController {
    private final SampleManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<SampleManageVO>> get(@AuthToken Token token, SampleManageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody SampleManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setCrtUid(jwtResolver.getUserId(jwt));
        request.setCrtIp(HttpHelper.getClientIp());
        service.create(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody SampleManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUdtUid(jwtResolver.getUserId(jwt));
        request.setUdtIp(HttpHelper.getClientIp());
        service.update(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @RequestBody SampleManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUdtUid(jwtResolver.getUserId(jwt));
        request.setUdtIp(HttpHelper.getClientIp());
        service.delete(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @GetMapping("/test")
    public ResponseEntity<List<SampleManageVO>> getTestInfo(@AuthToken Token token, SampleManageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.findTest(request));
    }
}