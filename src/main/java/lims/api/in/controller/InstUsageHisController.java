package lims.api.in.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.in.service.InstUsageHisService;
import lims.api.in.vo.InstUsageHisVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/in/instUsageHis")
public class InstUsageHisController {
    private final JwtResolver jwtResolver;
    private final InstUsageHisService service;

    @GetMapping
    public ResponseEntity<List<InstUsageHisVO>> get(@AuthToken Token token, InstUsageHisVO param) {
        param.setPlntCd(getPlantCode(token));
        return ResponseEntity.ok(service.find(param));
    }

    @GetMapping("/instrument")
    public ResponseEntity<List<InstUsageHisVO>> getInstrument(@AuthToken Token token, InstUsageHisVO request) {
        request.setPlntCd(getPlantCode(token));
        return ResponseEntity.ok(service.findInstrument(request));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody InstUsageHisVO request) {
        request.setPlntCd(getPlantCode(token));
        request.setAnsUid(getUserId(token));
        service.create(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody InstUsageHisVO request) {
        request.setPlntCd(getPlantCode(token));
        request.setUdtUid(getUserId(token));
        service.update(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @RequestBody InstUsageHisVO request) {
        request.setPlntCd(getPlantCode(token));
        request.setUdtUid(getUserId(token));
        service.delete(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/file")
    public ResponseEntity<Integer> saveFiles(@AuthToken Token token, InstUsageHisVO request) {
        request.setPlntCd(getPlantCode(token));
        return ResponseEntity.ok(service.updateFile(request));
    }

    private String getPlantCode(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }

    private String getUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}