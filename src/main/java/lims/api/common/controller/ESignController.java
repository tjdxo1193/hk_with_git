package lims.api.common.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.common.model.ESignAccountRequest;
import lims.api.config.AuthenticationConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/eSign")
@RequiredArgsConstructor
public class ESignController {

    private final AuthenticationConfig authenticationConfig;
    private final JwtResolver jwtResolver;

    @GetMapping("verification/account")
    public ResponseEntity<CommonResponse> verifyAccount(@AuthToken Token token, @Validated ESignAccountRequest request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        String loginId = jwtResolver.getLoginId(jwt);

        request.setPlntCd(plntCd);

        boolean isAuthenticated = authenticationConfig.authenticate(request.toAuthentication());
        boolean isEqualsLoginId = StringUtils.equals(loginId, request.getUsername());
        return ResponseEntity.ok(new CommonResponse(isAuthenticated && isEqualsLoginId));
    }

}