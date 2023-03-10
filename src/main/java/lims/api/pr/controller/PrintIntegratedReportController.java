package lims.api.pr.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.pr.dao.PrintIntegratedReportDao;
import lims.api.pr.service.PrintIntegratedReportService;
import lims.api.pr.vo.PrintIntegratedReportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/pr/printIntegratedReport")
public class PrintIntegratedReportController {
    private final JwtResolver jwtResolver;
    private final PrintIntegratedReportService service;

    @GetMapping
    public ResponseEntity<List<PrintIntegratedReportVO>> get(@AuthToken Token token, PrintIntegratedReportVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/testItem")
    public ResponseEntity<List<PrintIntegratedReportVO>> getTestItem(@AuthToken Token token, PrintIntegratedReportVO request) {
        return ResponseEntity.ok(service.findTestItem(request));
    }

    @GetMapping("/reportHistory")
    public ResponseEntity<List<PrintIntegratedReportVO>> getReportHistory(@AuthToken Token token, PrintIntegratedReportVO request) {
        return ResponseEntity.ok(service.findReportHistory(request));
    }

    @PostMapping("/save")
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody PrintIntegratedReportVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setWrtUid(jwtResolver.getUserId(jwt));
        service.save(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @GetMapping("/integratedReport")
    public ResponseEntity<PrintIntegratedReportVO> getIntegratedReport(PrintIntegratedReportVO request) {
        return ResponseEntity.ok(service.findReportDetail(request));
    }
}
