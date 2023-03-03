package lims.api.pr.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.pr.service.PrintReportService;
import lims.api.pr.vo.PrintReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/pr/printReport")
public class PrintReportController {

    private final PrintReportService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<PrintReportVO>> getTestReportList(@AuthToken Token token, PrintReportVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getTestReportList(request));
    }

    @GetMapping("/getTestItmList")
    public ResponseEntity<List<PrintReportVO>> getTestItmList(@AuthToken Token token, Integer ansIdx){
        String jwt = token.getJwt();
        PrintReportVO request = new PrintReportVO();
        request.setAnsIdx(ansIdx);
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getTestItmList(request));
    }

    @GetMapping("/reportPath")
    public ResponseEntity<String> getReportPath(@AuthToken Token token, PrintReportVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getReportPath(param));
    }


    @PostMapping
    public ResponseEntity<CommonResponse> updateRptInfo(@AuthToken Token token, @RequestBody PrintReportVO request) {

        service.updateRptInfo(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}