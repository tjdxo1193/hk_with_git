package lims.api.mt.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.mt.service.MonitorTestResultApprService;
import lims.api.mt.vo.MonitorTestResultApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/mt/monitorTestResultAppr")
public class MonitorTestResultApprController {

    private final MonitorTestResultApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<MonitorTestResultApprVO>> getMonitorTestResultApprList(@AuthToken Token token, MonitorTestResultApprVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.getMonitorTestResultApprList(request));
    }

    @GetMapping("getMonitorTestRst")
    public ResponseEntity<List<MonitorTestResultApprVO>> getMonitorTestRst(@AuthToken Token token, MonitorTestResultApprVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestRst(request));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody MonitorTestResultApprVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.approve(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @RequestBody MonitorTestResultApprVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        request.setRea(esign.getReason());
        service.reject(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/hold")
    public ResponseEntity<CommonResponse> hold(@AuthToken Token token, @RequestBody MonitorTestResultApprVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        request.setRea(esign.getReason());
        service.hold(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}