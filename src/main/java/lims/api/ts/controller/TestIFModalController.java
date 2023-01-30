package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.ts.service.TestIFModalService;
import lims.api.ts.vo.TestIFModalVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ts/testIFModal")
public class TestIFModalController {

    private final TestIFModalService service;
    private final JwtResolver jwtResolver;

    @GetMapping("/getSrmFinalOrderList")
    public ResponseEntity<List<TestIFModalVO>> getSrmFinalOrderList(@AuthToken Token token, TestIFModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSrmFinalOrderList(request));
    }

    @GetMapping("/getMesFinalOrderList")
    public ResponseEntity<List<TestIFModalVO>> getMesFinalOrderList(@AuthToken Token token, TestIFModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMesFinalOrderList(request));
    }

    @GetMapping("/getInpPerformanceList")
    public ResponseEntity<List<TestIFModalVO>> getInpPerformanceList(@AuthToken Token token, TestIFModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getInpPerformanceList(request));
    }

    @GetMapping("/getRelapsePrevList")
    public ResponseEntity<List<TestIFModalVO>> getRelapsePrevList(@AuthToken Token token, TestIFModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getRelapsePrevList(request));
    }

    @GetMapping("/getSrmReportList")
    public ResponseEntity<List<TestIFModalVO>> getSrmReportList(@AuthToken Token token, TestIFModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSrmReportList(request));
    }

    @GetMapping("/getPackingSpecList")
    public ResponseEntity<List<TestIFModalVO>> getPackingSpecList(@AuthToken Token token, TestIFModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPackingSpecList(request));
    }
}
