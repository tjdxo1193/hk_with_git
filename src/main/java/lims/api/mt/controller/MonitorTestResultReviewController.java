package lims.api.mt.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.mt.service.MonitorTestResultReviewService;
import lims.api.mt.vo.MonitorTestResultReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/mt/monitorTestResultReview")
public class MonitorTestResultReviewController {

    private final MonitorTestResultReviewService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<MonitorTestResultReviewVO>> getMonitorTestResultReviewList(@AuthToken Token token, MonitorTestResultReviewVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setRevwUid(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.getMonitorTestResultReviewList(request));
    }

    @GetMapping("/getMonitorTestRst")
    public ResponseEntity<List<MonitorTestResultReviewVO>> getMonitorTestRst(@AuthToken Token token, Integer mitmReqIdx){
        MonitorTestResultReviewVO request = new MonitorTestResultReviewVO();
        request.setMitmReqIdx(mitmReqIdx);
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestRst(request));
    }

    @PutMapping("/apprRequest")
    public ResponseEntity<CommonResponse> apprRequest(@AuthToken Token token, @RequestBody MonitorTestResultReviewVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.apprRequest(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @RequestBody MonitorTestResultReviewVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        request.setRea(esign.getReason());
        service.reject(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/hold")
    public ResponseEntity<CommonResponse> hold(@AuthToken Token token, @RequestBody MonitorTestResultReviewVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        request.setRea(esign.getReason());
        service.hold(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}