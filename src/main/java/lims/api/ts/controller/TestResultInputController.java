package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.ts.service.TestResultInputService;
import lims.api.ts.vo.TestResultApprVO;
import lims.api.ts.vo.TestResultInputVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ts/testResultInput")
public class TestResultInputController {

    private final TestResultInputService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestResultInputVO>> findAll(@AuthToken Token token, TestResultInputVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ansIdx}")
    public ResponseEntity<List<TestResultInputVO>> findResultItem(@PathVariable Integer ansIdx) {
        return ResponseEntity.ok(service.findResultItem(ansIdx));
    }

    @GetMapping("/resultHistory")
    public ResponseEntity<List<TestResultInputVO>> resultHistory(@AuthToken Token token, TestResultInputVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.resultHistory(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody TestResultInputVO param) {
        for(TestResultInputVO row : param.getEditedRowItems()) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setAnsDptCd(getAuthDptCd(token));
            row.setAnsUid(getAuthUserId(token));
        }
        service.save(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestReview")
    public ResponseEntity<CommonResponse> requestReview(@AuthToken Token token, @RequestBody TestResultInputVO param) {
        param.setRevwUid(param.getApproveInfo().getAprUid());
        service.requestReview(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/publishEvent")
    public ResponseEntity<CommonResponse> publishEvent(@AuthToken Token token, @RequestBody TestResultInputVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        service.publishEvent(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/savedFile")
    public ResponseEntity<Integer> savedFile(@AuthToken Token token, TestResultInputVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.savedFile(param));
    }

    @PutMapping("/requestHold")
    public ResponseEntity<CommonResponse> requestHold(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody TestResultInputVO param) {
        param.setHldUid(getAuthUserId(token));
        param.setHldRea(esign.getReason());
        service.requestHold(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    private String getAuthUserPlntCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }

    private String getAuthDptCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getDptCd(jwt);
    }
}