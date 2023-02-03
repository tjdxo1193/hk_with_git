package lims.api.mt.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.mt.service.MonitorTestResultInputService;
import lims.api.mt.vo.MonitorTestResultApprVO;
import lims.api.mt.vo.MonitorTestResultInputVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/mt/monitorTestResultInput")
public class MonitorTestResultInputController {

    private final MonitorTestResultInputService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<MonitorTestResultInputVO>> getMonitorTestResultInputList(@AuthToken Token token, MonitorTestResultInputVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setCrgDptNm(jwtResolver.getDptCd(jwt));
        return ResponseEntity.ok(service.getMonitorTestResultInputList(request));
    }

    @GetMapping("getMonitorTestRst")
    public ResponseEntity<List<MonitorTestResultInputVO>> getMonitorTestRst(@AuthToken Token token, MonitorTestResultInputVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestRst(request));
    }

    @PutMapping("/updateRst")
    public ResponseEntity<CommonResponse> updateRst(@RequestBody MonitorTestResultInputVO request) {
        service.updateRst(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestReview")
    public ResponseEntity<CommonResponse> requestReview(@RequestBody MonitorTestResultInputVO request, @ESign ESignInfo esign) {
        request.setRea(esign.getReason());
        service.requestReview(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/hold")
    public ResponseEntity<CommonResponse> hold(@AuthToken Token token, @RequestBody MonitorTestResultInputVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        request.setRea(esign.getReason());
        service.hold(request);
        return ResponseEntity.ok(new CommonResponse());
    }

}