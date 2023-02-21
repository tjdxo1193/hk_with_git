package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.schedule.service.impl.StabTestScheduler;
import lims.api.ts.service.TestReceiptService;
import lims.api.ts.vo.TestReceiptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ts/testReceipt")
public class TestReceiptController {

    private final TestReceiptService service;
    private final JwtResolver jwtResolver;
    private final StabTestScheduler scheduler;

    @GetMapping
    public ResponseEntity<List<TestReceiptVO>> getTestReceiptList(@AuthToken Token token, TestReceiptVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getTestReceiptList(request));
    }

    @GetMapping("getTestAitm")
    public ResponseEntity<List<TestReceiptVO>> getTestAitm(@AuthToken Token token, TestReceiptVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getTestAitm(request));
    }

    @PutMapping("/receipt")
    public ResponseEntity<CommonResponse> receipt(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<TestReceiptVO> request) {
        String jwt = token.getJwt();
        for(TestReceiptVO item : request) {
            item.setRcpUid(jwtResolver.getUserId(jwt));
            item.setRcpDptCd(jwtResolver.getDptCd(jwt));
            item.setRcpRmk(esign.getReason());
        }
        service.receipt(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @GetMapping("getNonconformityTestList")
    public ResponseEntity<List<TestReceiptVO>> getNonconformityTestList(@AuthToken Token token, TestReceiptVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getNonconformityTestList(request));
    }

    @PostMapping("/requestCreate")
    public void requestCreate(){
        scheduler.run();
    }

}