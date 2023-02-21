package lims.api.sc.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sc.service.InspectPurchasingPerformanceService;
import lims.api.sc.vo.InspectPurchasingPerformanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sc/inspectPurchasingPerformance")
public class InspectPurchasingPerformanceController {
    private final InspectPurchasingPerformanceService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<InspectPurchasingPerformanceVO>> get(@AuthToken Token token, InspectPurchasingPerformanceVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/{plntCd}/{pitmCd}/{phsOrderNo}")
    public ResponseEntity<List<InspectPurchasingPerformanceVO>> getDetail(@PathVariable String plntCd, @PathVariable String pitmCd
        ,@PathVariable String phsOrderNo) {
        return ResponseEntity.ok(service.findDetail(plntCd, pitmCd, phsOrderNo));
    }

    @GetMapping("/{ispPhsPfaIdx}")
    public ResponseEntity<List<InspectPurchasingPerformanceVO>> getRecord(@PathVariable Integer ispPhsPfaIdx) {
        return ResponseEntity.ok(service.getRecord(ispPhsPfaIdx));
    }

    @PostMapping
    public ResponseEntity<String> send(@AuthToken Token token, @RequestBody List<InspectPurchasingPerformanceVO> list) {
        String userId = getAuthUserId(token);
        for (InspectPurchasingPerformanceVO param : list) {
            param.setHoprIfUid(userId);
        }
        return ResponseEntity.ok(service.send(list));
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }


}
