package lims.api.tp.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.tp.service.SampleUsageService;
import lims.api.tp.vo.SampleUsageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/tp/sampleUsage")
public class SampleUsageController {
    private final SampleUsageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<SampleUsageVO>> get(@AuthToken Token token, SampleUsageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/sample")
    public ResponseEntity<List<SampleUsageVO>> getSample(@AuthToken Token token, SampleUsageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.findSample(request));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody SampleUsageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        service.create(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody SampleUsageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        service.update(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @RequestBody SampleUsageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        service.delete(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestApproveUse")
    public ResponseEntity<CommonResponse> requestUse(@AuthToken Token token, @RequestBody SampleUsageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        service.requestApproveUsage(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestApproveCancelUse")
    public ResponseEntity<CommonResponse> requestCancelUse(@AuthToken Token token, @RequestBody SampleUsageVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        service.requestCancelUsage(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}