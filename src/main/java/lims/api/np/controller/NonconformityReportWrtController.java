package lims.api.np.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.np.service.NonconformityReportWrtService;
import lims.api.np.vo.NonconformityReportWrtVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/np/nonconformityReportWrt")
public class NonconformityReportWrtController {

    private final NonconformityReportWrtService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<NonconformityReportWrtVO>> findAll(@AuthToken Token token, NonconformityReportWrtVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("findResultItem")
    public ResponseEntity<List<NonconformityReportWrtVO>> findResultItem(@AuthToken Token token, NonconformityReportWrtVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findResultItem(param));
    }

    @PutMapping("/save")
    public ResponseEntity<CommonResponse> save(@RequestBody NonconformityReportWrtVO param) {
        service.save(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/request")
    public ResponseEntity<CommonResponse> request(@RequestBody NonconformityReportWrtVO param) {
        service.request(param);
        return ResponseEntity.ok(new CommonResponse());
    }
}
