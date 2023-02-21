package lims.api.mt.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.mt.service.MonitorTestInstructionService;
import lims.api.mt.vo.MonitorTestInstructionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/mt/monitorTestInstruction")
public class MonitorTestInstructionController {

    private final MonitorTestInstructionService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<MonitorTestInstructionVO>> getMonitorTestInstructionList(@AuthToken Token token, MonitorTestInstructionVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setUserId(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.getMonitorTestInstructionList(request));
    }

    @GetMapping("getMonitorTestRst")
    public ResponseEntity<List<MonitorTestInstructionVO>> getMonitorTestRst(@AuthToken Token token, MonitorTestInstructionVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getMonitorTestRst(request));
    }

    @PutMapping("/instruct")
    public ResponseEntity<CommonResponse> instruct(@AuthToken Token token, @RequestBody List<MonitorTestInstructionVO> request, @ESign ESignInfo esign) {
        for(MonitorTestInstructionVO item : request) {
            String jwt = token.getJwt();
            item.setPlntCd(jwtResolver.getPlantCode(jwt));
            item.setAssSpcc(esign.getReason());
        }
        service.instruct(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/deleteRst")
    public ResponseEntity<CommonResponse> deleteRst(@AuthToken Token token, @RequestBody List<MonitorTestInstructionVO> request) {
        service.deleteRst(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}