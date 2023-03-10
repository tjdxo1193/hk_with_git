package lims.api.sy.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.Permit;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.sy.model.AuditTrailSearchResponse;
import lims.api.sy.service.AuditTrailSearchService;
import lims.api.sy.vo.AuditTrailSearchConditionVO;
import lims.api.sy.vo.AuditTrailSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/sys/auditTrail")
@RequiredArgsConstructor
public class AuditTrailSearchController {

    private final AuditTrailSearchService searchService;
    private final JwtResolver jwtResolver;

    @Permit
    @GetMapping
    public ResponseEntity<AuditTrailSearchResponse> auditList(@AuthToken Token token, AuditTrailSearchConditionVO request) {
        AuditTrailSearchVO param = new AuditTrailSearchVO();
        param.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        param.setIp(request.getIp());
        param.setMenuCd(request.getMenuCode());
        param.setSearchRange(request.getSearchRange());
        return ResponseEntity.ok(searchService.getAudits(param));
    }

}