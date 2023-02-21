package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.ts.service.TestRequestService;
import lims.api.ts.vo.TestRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ts/testRequest")
public class TestRequestController {

    private final TestRequestService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestRequestVO>> getTestRequestList(@AuthToken Token token, TestRequestVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getTestRequestList(request));
    }

    @PutMapping("/requestRegist")
    public ResponseEntity<CommonResponse> requestRegist(@AuthToken Token token, @RequestBody TestRequestVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.requestRegist(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/save")
    public ResponseEntity<CommonResponse> save( @RequestBody TestRequestVO request) {
        service.save(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/request")
    public ResponseEntity<CommonResponse> request(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<TestRequestVO> request) {
        String jwt = token.getJwt();
        for(TestRequestVO item : request) {
            item.setReqUid(jwtResolver.getUserId(jwt));
            item.setReqDptCd(jwtResolver.getDptCd(jwt));
            item.setReqRmk(esign.getReason());
        }
        service.request(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @GetMapping("getTestList")
    public ResponseEntity<List<TestRequestVO>> getTestList(@AuthToken Token token, TestRequestVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getTestList(request));
    }
}