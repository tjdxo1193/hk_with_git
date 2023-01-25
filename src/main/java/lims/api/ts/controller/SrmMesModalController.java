package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.ts.service.SrmMesModalService;
import lims.api.ts.vo.SrmMesModalVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ts/srmMesModal")
public class SrmMesModalController {

    private final SrmMesModalService service;
    private final JwtResolver jwtResolver;

    @GetMapping("/getSrmFinalOrderList")
    public ResponseEntity<List<SrmMesModalVO>> getSrmFinalOrderList(@AuthToken Token token, SrmMesModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSrmFinalOrderList(request));
    }

    @GetMapping("/getMesFinalOrderList")
    public ResponseEntity<List<SrmMesModalVO>> getMesFinalOrderList(@AuthToken Token token, SrmMesModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMesFinalOrderList(request));
    }

    @GetMapping("/getRelapsePrevList")
    public ResponseEntity<List<SrmMesModalVO>> getRelapsePrevList(@AuthToken Token token, SrmMesModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getRelapsePrevList(request));
    }

    @GetMapping("/getSrmReportList")
    public ResponseEntity<List<SrmMesModalVO>> getSrmReportList(@AuthToken Token token, SrmMesModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSrmReportList(request));
    }

    @GetMapping("/getPackingSpecList")
    public ResponseEntity<List<SrmMesModalVO>> getPackingSpecList(@AuthToken Token token, SrmMesModalVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPackingSpecList(request));
    }
}
