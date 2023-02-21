package lims.api.np.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.np.service.NonconformityReportSearchService;
import lims.api.np.vo.NonconformityReportApprVO;
import lims.api.np.vo.NonconformityReportSearchVO;
import lims.api.ts.vo.TestUnsuitableListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/np/nonconformityReportSearch")
public class NonconformityReportSearchController {

    private final NonconformityReportSearchService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<NonconformityReportSearchVO>> findAll(@AuthToken Token token, NonconformityReportSearchVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("findResultItem")
    public ResponseEntity<List<NonconformityReportSearchVO>> findResultItem(@AuthToken Token token, NonconformityReportSearchVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findResultItem(param));
    }
}
