package lims.api.re.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.re.service.ReagentOpenCancelService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/re/reagentOpenCancel")
public class ReagentOpenCancelController {

    private final ReagentOpenCancelService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<ReagentMaterialVO>> findAll(@AuthToken Token token, ReagentMaterialVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> cancel(@AuthToken Token token, @ESign ESignInfo esign, @RequestBody List<ReagentMaterialVO> list) {
        String jwt = token.getJwt();
        for(ReagentMaterialVO row : list) {
            row.setPlntCd(jwtResolver.getPlantCode(jwt));
            row.setOpnCanlUid(jwtResolver.getUserId(jwt));
            row.setOpnCanlRea(esign.getReason());
        }

        service.cancel(list);
        return ResponseEntity.ok(new CommonResponse());
    }
}