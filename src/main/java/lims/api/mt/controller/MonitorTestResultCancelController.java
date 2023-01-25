package lims.api.mt.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.mt.service.MonitorTestResultCancelService;
import lims.api.mt.vo.MonitorTestResultCancelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/mt/monitorTestResultCancel")
public class MonitorTestResultCancelController {

    private final MonitorTestResultCancelService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<MonitorTestResultCancelVO>> getMonitorTestResultCancelList(@AuthToken Token token, MonitorTestResultCancelVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestResultCancelList(request));
    }

    @GetMapping("getMonitorTestRst")
    public ResponseEntity<List<MonitorTestResultCancelVO>> getMonitorTestRst(@AuthToken Token token, MonitorTestResultCancelVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestRst(request));
    }

    @PutMapping("/cancel")
    public ResponseEntity<CommonResponse> cancel(@AuthToken Token token, @RequestBody MonitorTestResultCancelVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        request.setRea(esign.getReason());
        service.cancel(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/holdnOff")
    public ResponseEntity<CommonResponse> holdnOff(@AuthToken Token token, @RequestBody MonitorTestResultCancelVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        request.setRea(esign.getReason());
        service.holdnOff(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}