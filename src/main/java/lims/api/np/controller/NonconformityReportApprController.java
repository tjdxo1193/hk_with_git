package lims.api.np.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.np.service.NonconformityReportApprService;
import lims.api.np.vo.NonconformityReportApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/np/nonconformityReportAppr")
public class NonconformityReportApprController {

    private final NonconformityReportApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<NonconformityReportApprVO>> findAll(@AuthToken Token token, NonconformityReportApprVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setUserId(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("findResultItem")
    public ResponseEntity<List<NonconformityReportApprVO>> findResultItem(@AuthToken Token token, NonconformityReportApprVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findResultItem(param));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@RequestBody NonconformityReportApprVO param) {
        service.approve(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@RequestBody NonconformityReportApprVO param) {
        service.reject(param);
        return ResponseEntity.ok(new CommonResponse());
    }


}
