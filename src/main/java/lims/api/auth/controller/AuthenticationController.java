package lims.api.auth.controller;

import lims.api.auth.annotation.Permit;
import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.auth.domain.AccountAuthentication;
import lims.api.auth.domain.Token;
import lims.api.auth.domain.TokenAuthentication;
import lims.api.auth.exception.UnauthenticatedException;
import lims.api.auth.model.AuthenticationRequest;
import lims.api.auth.model.TokenRequest;
import lims.api.auth.model.TokenResponse;
import lims.api.auth.properties.AuthProperties;
import lims.api.auth.service.TokenAuthenticationConfigurer;
import lims.api.auth.service.TokenHttpResolver;
import lims.api.auth.service.impl.Authenticator;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.auth.session.TokenSession;
import lims.api.common.message.MessageUtil;
import lims.api.common.model.CommonResponse;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("${apiPrefix}/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthProperties properties;
    private final Authenticator authenticator;
    private final JwtResolver jwtResolver;
    private final TokenHttpResolver tokenHttpResolver;
    private final TokenAuthenticationConfigurer authenticationConfigure;

    @Permit
    @PostMapping("token")
    public ResponseEntity<TokenResponse> authenticateByAccount(@Validated @RequestBody AuthenticationRequest authenticationRequest, HttpSession session) {
        AccountAuthentication accountAuthentication = null;

        try {
            accountAuthentication = authenticationRequest.toAuthentication();
            TokenAuthentication tokenAuthentication = authenticator.authenticate(accountAuthentication);

            Token refreshToken = tokenAuthentication.getRefreshToken();
            setRefreshTokenToSession(session, authenticationRequest.getUsername(), refreshToken.getJwt());

            authenticationConfigure.onLogin(accountAuthentication.toSafeAuthentication());

            return ResponseEntity.ok()
                    .headers(tokenHttpResolver.makeResponseHeader(tokenAuthentication))
                    .body(tokenHttpResolver.makeResponseBody(tokenAuthentication));
        } catch (Exception e) {
            SafeAccountAuthentication authentication = Optional.ofNullable(accountAuthentication)
                    .orElse(AccountAuthentication.builder().build())
                    .toSafeAuthentication();
            authenticationConfigure.onLoginFailed(authentication);
            throw e;
        }
    }

    @Permit
    @PostMapping("token/reissue")
    public ResponseEntity<TokenResponse> refreshToken(@Validated @RequestBody TokenRequest tokenRequest, HttpSession session) {
        String refreshToken = tokenRequest.getRefreshToken();

        if (Strings.isEmpty(refreshToken)) {
            throw new IllegalArgumentException("auth.error.notFoundToken");
        }

        String sessionKey = jwtResolver.getUsername(refreshToken);
        boolean isExistsSession = TokenSession.exists(session, sessionKey);
        if (!isExistsSession) {
            throw new UnauthenticatedException("auth.error.revokedToken");
        }

        boolean isValidRefreshToken = authenticator.verify(refreshToken);
        if (!isValidRefreshToken) {
            throw new UnauthenticatedException("auth.error.invalidToken");
        }

        boolean isEqualsSessionToken = TokenSession.equals(session, sessionKey, refreshToken);
        if (!isEqualsSessionToken) {
            throw new UnauthenticatedException("auth.error.revokedToken");
        }

        TokenAuthentication newAuthentication = authenticator.reauthenticate(refreshToken);
        Token newRefreshToken = newAuthentication.getRefreshToken();
        setRefreshTokenToSession(session, sessionKey, newRefreshToken.getJwt());

        return ResponseEntity.ok()
                .headers(tokenHttpResolver.makeResponseHeader(newAuthentication))
                .body(tokenHttpResolver.makeResponseBody(newAuthentication));
    }

    @Permit
    @GetMapping("token/verification")
    public ResponseEntity<CommonResponse> verifyToken(@AuthToken Token token, HttpSession session) {
        try {
            String jwt = token.getJwt();
            boolean isValidToken = authenticator.verify(jwt);
            if (!isValidToken) {
                String message = MessageUtil.getMessage("auth.error.invalidToken");
                return ResponseEntity.ok().body(new CommonResponse(false, message));
            }

            String sessionKey = jwtResolver.getUsername(jwt);
            boolean isExistsRefreshTokenInSession = TokenSession.exists(session, sessionKey);
            if (!isExistsRefreshTokenInSession) {
                String message = MessageUtil.getMessage("auth.error.revokedToken");
                return ResponseEntity.ok().body(new CommonResponse(false, message));
            }

            return ResponseEntity.ok().body(new CommonResponse());
        } catch (Exception e) {
            log.error("[{}] throw error during verify token, {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
            return ResponseEntity.ok().body(new CommonResponse(false));
        }
    }

    @Permit
    @PostMapping("token/revocation")
    public ResponseEntity<CommonResponse> revokeToken(@AuthToken Token token, HttpSession session) {
        String jwt = token.getJwt();

        if (Strings.isEmpty(jwt)) {
            return ResponseEntity.ok().body(new CommonResponse(false, MessageUtil.getMessage("auth.error.notFoundToken")));
        }

        String sessionKey = jwtResolver.getUsername(jwt);
        boolean isExistsRefreshTokenInSession = TokenSession.exists(session, sessionKey);
        if (isExistsRefreshTokenInSession) {
            TokenSession.remove(session, sessionKey);

            SafeAccountAuthentication authentication = SafeAccountAuthentication.builder()
                    .plntCd(jwtResolver.getPlantCode(jwt))
                    .username(jwtResolver.getLoginId(jwt))
                    .build();
            authenticationConfigure.onLogout(authentication);
        }

        return ResponseEntity.ok().body(new CommonResponse(MessageUtil.getMessage("auth.token.revoked")));
    }

    private void setRefreshTokenToSession(HttpSession session, String key, String refreshToken) {
        TokenSession.set(session, key, refreshToken, properties.getTokenSessionMaxInactiveTime());
    }

}