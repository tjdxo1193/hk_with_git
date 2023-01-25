package lims.api.sc.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sc.service.InspectPurchasingPerformanceService;
import lims.api.sc.vo.InspectPurchasingPerformanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/request")
    public ResponseEntity<List<InspectPurchasingPerformanceVO>> getRequest(@AuthToken Token token, InspectPurchasingPerformanceVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.findRequest(request));
    }
}
