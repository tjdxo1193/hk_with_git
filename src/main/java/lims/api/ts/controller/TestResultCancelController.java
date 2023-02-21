package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.ts.service.TestResultCancelService;
import lims.api.ts.vo.TestResultCancelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}//ts/testResultCancel")
public class TestResultCancelController {

    private final TestResultCancelService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestResultCancelVO>> findAll(@AuthToken Token token, TestResultCancelVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ansIdx}")
    public ResponseEntity<List<TestResultCancelVO>> findResultItem(@PathVariable Integer ansIdx) {
        return ResponseEntity.ok(service.findResultItem(ansIdx));
    }

    @PutMapping("/testCancel")
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<TestResultCancelVO> list) {
        for(TestResultCancelVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setAnsCanlUid(getAuthUserId(token));
            row.setAnsCanlRea(esign.getReason());
        }
        service.testCancel(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/hold")
    public ResponseEntity<CommonResponse> hold(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<TestResultCancelVO> list) {
        for(TestResultCancelVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setHldUid(getAuthUserId(token));
            row.setHldRea(esign.getReason());
        }
        service.hold(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/holdOff")
    public ResponseEntity<CommonResponse> holdOff(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<TestResultCancelVO> list) {
        for(TestResultCancelVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setHldCanlUid(getAuthUserId(token));
            row.setHldCanlRea(esign.getReason());
        }
        service.holdOff(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/savedFile")
    public ResponseEntity<Integer> savedFile(@AuthToken Token token, TestResultCancelVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.savedFile(param));
    }

    private String getAuthUserPlntCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}