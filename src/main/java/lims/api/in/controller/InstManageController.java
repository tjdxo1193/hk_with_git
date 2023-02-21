package lims.api.in.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.in.service.InstManageService;
import lims.api.in.vo.InstManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/in/instManage")
public class InstManageController {
    private final InstManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<InstManageVO>> get(@AuthToken Token token, InstManageVO request) {
        request.setPlntCd(getPlantCode(token));
        return ResponseEntity.ok(service.find(request));
    }

    @GetMapping("/accessory")
    public ResponseEntity<List<InstManageVO>> getAccessory(@AuthToken Token token, InstManageVO request) {
        request.setPlntCd(getPlantCode(token));
        return ResponseEntity.ok(service.findAccessory(request));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody InstManageVO request) {
        request.setPlntCd(getPlantCode(token));
        request.setUdtUid(getUserId(token));
        request.setCrtUid(getUserId(token));
        service.create(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody InstManageVO request) {
        request.setPlntCd(getPlantCode(token));
        request.setUdtUid(getUserId(token));
        service.update(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @RequestBody InstManageVO request) {
        request.setPlntCd(getPlantCode(token));
        request.setUdtUid(getUserId(token));
        service.delete(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/file")
    public ResponseEntity<Integer> saveFile(@AuthToken Token token, InstManageVO request) {
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