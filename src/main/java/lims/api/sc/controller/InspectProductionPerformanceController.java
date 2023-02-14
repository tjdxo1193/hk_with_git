package lims.api.sc.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sc.service.InspectProductionPerformanceService;
import lims.api.sc.vo.InspectProductionPerformanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sc/inspectProductionPerformance")
public class InspectProductionPerformanceController {

    private final InspectProductionPerformanceService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<InspectProductionPerformanceVO>> get(InspectProductionPerformanceVO request) {
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/{mtrCd}/{pdtOrderNo}/{batchNo}/{etrDt}")
    public ResponseEntity<List<InspectProductionPerformanceVO>> getDetail(@PathVariable String mtrCd, @PathVariable String pdtOrderNo
                        ,@PathVariable String batchNo, @PathVariable String etrDt) {
        return ResponseEntity.ok(service.findDetail(mtrCd, pdtOrderNo, batchNo, etrDt));
    }

    @GetMapping("/{ispPdtPfaIdx}")
    public ResponseEntity<List<InspectProductionPerformanceVO>> getRecord(@PathVariable Integer ispPdtPfaIdx) {
        return ResponseEntity.ok(service.getRecord(ispPdtPfaIdx));
    }

    @PostMapping
    public ResponseEntity<String> send(@AuthToken Token token, @RequestBody List<InspectProductionPerformanceVO> list) {
        String userId = getAuthUserId(token);
        for (InspectProductionPerformanceVO param : list) {
            param.setHoprIfUid(userId);
        }
        return ResponseEntity.ok(service.send(list));
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}
