package lims.api.sm.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sm.service.ProcessSpecimenManageService;
import lims.api.sm.vo.ProcessSpecimenManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sm/processSpecimenManage")
public class ProcessSpecimenManageController {

    private final ProcessSpecimenManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<ProcessSpecimenManageVO>> getProcessSpecimenList(@AuthToken Token token, ProcessSpecimenManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getProcessSpecimenList(request));
    }

    @PutMapping("/save")
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody ProcessSpecimenManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.save(request);
        return ResponseEntity.ok(new CommonResponse());
    }

}