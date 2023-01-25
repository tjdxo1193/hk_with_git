package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.ts.service.TestResultReviewService;
import lims.api.ts.vo.TestResultReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ts/testResultReview")
public class TestResultReviewController {

    private final TestResultReviewService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestResultReviewVO>> findAll(@AuthToken Token token, TestResultReviewVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        param.setRevwUid(getAuthUserId(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ansIdx}")
    public ResponseEntity<List<TestResultReviewVO>> findResultItem(@PathVariable Integer ansIdx) {
        return ResponseEntity.ok(service.findResultItem(ansIdx));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> completedReview(@AuthToken Token token, @ESign ESignInfo esign,@RequestBody TestResultReviewVO param) {
        param.setRevwUid(getAuthUserId(token));
        param.setRevwCmmt(esign.getReason());
        service.completedReview(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestHold")
    public ResponseEntity<CommonResponse> requestHold(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody TestResultReviewVO param) {
        param.setHldUid(getAuthUserId(token));
        param.setHldRea(esign.getReason());
        service.requestHold(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/requestRejection")
    public ResponseEntity<CommonResponse> requestRejection(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody TestResultReviewVO param) {
        param.setRjtUid(getAuthUserId(token));
        param.setRjtRea(esign.getReason());
        service.requestRejection(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/savedFile")
    public ResponseEntity<Integer> savedFile(@AuthToken Token token, TestResultReviewVO param) {
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