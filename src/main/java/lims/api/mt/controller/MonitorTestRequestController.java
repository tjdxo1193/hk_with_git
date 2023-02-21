package lims.api.mt.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.mt.service.MonitorTestRequestService;
import lims.api.mt.vo.MonitorTestRequestVO;
import lims.api.schedule.service.impl.MonitorTestScheduler;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/mt/monitorTestRequest")
public class MonitorTestRequestController {

    private final MonitorTestRequestService service;
    private final JwtResolver jwtResolver;
    private final MonitorTestScheduler scheduler;

    @GetMapping
    public ResponseEntity<List<MonitorTestRequestVO>> getMonitorTestRequestList(@AuthToken Token token, MonitorTestRequestVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestRequestList(request));
    }

    @PutMapping("/request")
    public ResponseEntity<CommonResponse> request(@AuthToken Token token, @RequestBody List<MonitorTestRequestVO> request, @ESign ESignInfo esign) {
        for(MonitorTestRequestVO item : request) {
            String jwt = token.getJwt();
            item.setReqUid(jwtResolver.getUserId(jwt));
            item.setReqDptCd(jwtResolver.getDptCd(jwt));
            item.setReqRmk(esign.getReason());
        }
        service.request(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestCancel")
    public ResponseEntity<CommonResponse> requestCancel(@AuthToken Token token, @RequestBody List<MonitorTestRequestVO> request, @ESign ESignInfo esign) {
        for(MonitorTestRequestVO item : request) {
            String jwt = token.getJwt();
            item.setReqCanlUid(jwtResolver.getUserId(jwt));
            item.setReqCanlRea(esign.getReason());
        }
        service.requestCancel(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/requestCreate")
    public void requestCreate(){
        scheduler.run();
    }
}