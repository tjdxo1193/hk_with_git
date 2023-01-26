package lims.api.sc.controller;

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

    @GetMapping
    public ResponseEntity<List<InspectProductionPerformanceVO>> get(InspectProductionPerformanceVO request) {
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/detail")
    public ResponseEntity<List<InspectProductionPerformanceVO>> getDetail(InspectProductionPerformanceVO request) {
        return ResponseEntity.ok(service.findDetail(request));
    }
}