package lims.api.common.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.Permit;
import lims.api.auth.domain.Token;
import lims.api.auth.exception.UnauthenticatedException;
import lims.api.auth.service.impl.Authenticator;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.common.service.AuthorizationService;
import lims.api.common.service.impl.ErrorService;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("${apiPrefix}/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    private final Authenticator authenticator;
    private final JwtResolver jwtResolver;
    private final AuthorizationService authorizationService;
    private final ErrorService errorService;

    @Permit
    @GetMapping("menus/{menuCode}/allowed")
    public ResponseEntity<CommonResponse> isAllowedMenu(@AuthToken Token token, @PathVariable("menuCode") String menuCd) {
        String jwt = null;
        String userId = null;
        try {
            jwt = token.getJwt();
            if (jwt == null) {
                throw new UnauthenticatedException("auth.error.notFoundToken");
            }

            boolean isValidToken = authenticator.verify(jwt);
            if (!isValidToken) {
                throw new UnauthenticatedException("auth.error.invalidToken");
            }

            userId = jwtResolver.getUserId(jwt);
            boolean isAllowed = authorizationService.isAllowedMenu(userId, menuCd);
            return ResponseEntity.ok(new CommonResponse(isAllowed));
        } catch (UnauthenticatedException e) {
            log.error("[{}] No exists Token or Invalid Token. jwt: {}", ThreadUtil.getCurrentMethodName(), jwt);
            throw e;
        } catch (Exception e) {
            log.warn("[{}] Not allowed auth token. userId: {}, jwt: {}", ThreadUtil.getCurrentMethodName(), userId, jwt);
            errorService.record(e);
            return ResponseEntity.ok(new CommonResponse(false, e.getMessage()));
        }
    }

}