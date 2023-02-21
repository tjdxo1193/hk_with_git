package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.ts.service.TestInstructionService;
import lims.api.ts.vo.TestInstructionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ts/testInstruction")
public class TestInstructionController {

    private final TestInstructionService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestInstructionVO>> getTestInstructList(@AuthToken Token token, TestInstructionVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.getTestInstructList(request));
    }

    @GetMapping("/getTestAitm")
    public ResponseEntity<List<TestInstructionVO>> getTestAitm(@AuthToken Token token, Integer ansIdx){
        String jwt = token.getJwt();
        TestInstructionVO request = new TestInstructionVO();
        request.setAnsIdx(ansIdx);
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getTestAitm(request));
    }

    @PutMapping("/instruct")
    public ResponseEntity<CommonResponse> instruct(@AuthToken Token token, @RequestBody List<TestInstructionVO> request, @ESign ESignInfo esign) {
        for(TestInstructionVO item : request) {
            item.setAssSpcc(esign.getReason());
        }
        service.instruct(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/deleteRst")
    public ResponseEntity<CommonResponse> deleteRst(@AuthToken Token token, @RequestBody List<TestInstructionVO> request) {
        service.deleteRst(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/saveFile")
    public ResponseEntity<Integer> saveFile(@AuthToken Token token, TestInstructionVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.saveFile(request));
    }

}