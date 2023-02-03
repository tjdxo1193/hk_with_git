package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.ts.service.TestResultApprService;
import lims.api.ts.vo.TestResultApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}//ts/testResultAppr")
public class TestResultApprController {

    private final TestResultApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestResultApprVO>> findAll(@AuthToken Token token, TestResultApprVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        param.setAprUid(getAuthUserId(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ansIdx}")
    public ResponseEntity<List<TestResultApprVO>> findResultItem(@PathVariable Integer ansIdx) {
        return ResponseEntity.ok(service.findResultItem(ansIdx));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> completedReview(@AuthToken Token token, @RequestBody List<TestResultApprVO> list) {
        for(TestResultApprVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
        }
        service.approve(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestHold")
    public ResponseEntity<CommonResponse> requestHold(@AuthToken Token token, @RequestBody List<TestResultApprVO> list, @ESign ESignInfo esign) {
        for(TestResultApprVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setHldUid(getAuthUserId(token));
            row.setHldRea(esign.getReason());
        }
        service.requestHold(list);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestRejection")
    public ResponseEntity<CommonResponse> requestRejection(@AuthToken Token token, @RequestBody List<TestResultApprVO> list, @ESign ESignInfo esign) {
        for(TestResultApprVO row : list) {
            row.setPlntCd(getAuthUserPlntCd(token));
            row.setRjtUid(getAuthUserId(token));
            row.setRjtRea(esign.getReason());
        }
        service.requestRejection(list);
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
}