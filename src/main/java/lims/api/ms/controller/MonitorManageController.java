package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.MonitorManageService;
import lims.api.ms.vo.MonitorManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/monitorManage")
public class MonitorManageController {
    private final MonitorManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<MonitorManageVO>> get(@AuthToken Token token,  MonitorManageVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        request.setPlntCd(plntCd);
        return ResponseEntity.ok(service.find(request));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@AuthToken Token token, @RequestBody MonitorManageVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        request.setPlntCd(plntCd);
        service.insert(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody MonitorManageVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        request.setPlntCd(plntCd);
        service.update(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @RequestBody MonitorManageVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        request.setPlntCd(plntCd);
        service.delete(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}