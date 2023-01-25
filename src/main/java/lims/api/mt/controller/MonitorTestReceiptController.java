package lims.api.mt.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.mt.service.MonitorTestReceiptService;
import lims.api.mt.vo.MonitorTestReceiptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/mt/monitorTestReceipt")
public class MonitorTestReceiptController {

    private final MonitorTestReceiptService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<MonitorTestReceiptVO>> getMonitorTestReceiptList(@AuthToken Token token, MonitorTestReceiptVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestReceiptList(request));
    }

    @GetMapping("/getMonitorTestMitm")
    public ResponseEntity<List<MonitorTestReceiptVO>> getMonitorTestMitm(@AuthToken Token token, Integer aitmSpecIdx){
        MonitorTestReceiptVO request = new MonitorTestReceiptVO();
        request.setAitmSpecIdx(aitmSpecIdx);
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestMitm(request));
    }

    @PutMapping("/receipt")
    public ResponseEntity<CommonResponse> receipt(@AuthToken Token token, @RequestBody List<MonitorTestReceiptVO> request) {
        String jwt = token.getJwt();
        for(MonitorTestReceiptVO item : request) {
            item.setPlntCd(jwtResolver.getPlantCode(jwt));
            item.setRcpUid(jwtResolver.getUserId(jwt));
            item.setRcpDptCd(jwtResolver.getDptCd(jwt));
        }
        service.receipt(request);
        return ResponseEntity.ok(new CommonResponse());
    }

}