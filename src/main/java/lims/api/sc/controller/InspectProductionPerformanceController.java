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

    @GetMapping("/{pitmCd}/{pdtOrderNo}/{batchNo}/{ispReqDt}")
    public ResponseEntity<List<InspectProductionPerformanceVO>> getDetail(@PathVariable String pitmCd, @PathVariable String pdtOrderNo
                        ,@PathVariable String batchNo, @PathVariable String ispReqDt) {
        return ResponseEntity.ok(service.findDetail(pitmCd, pdtOrderNo, batchNo, ispReqDt));
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
