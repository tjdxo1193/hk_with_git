package lims.api.sc.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sc.service.InspectProductionPerformanceService;
import lims.api.sc.vo.InspectProductionPerformanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sc/inspectProductionPerformance")
public class InspectProductionPerformanceController {
    private final InspectProductionPerformanceService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<InspectProductionPerformanceVO>> get(@AuthToken Token token, InspectProductionPerformanceVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }
}
