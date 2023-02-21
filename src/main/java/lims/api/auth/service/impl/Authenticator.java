package lims.api.auth.service.impl;

import lims.api.auth.domain.*;
import lims.api.auth.exception.UnauthenticatedException;
import lims.api.auth.service.TokenAuthenticationConfigurer;
import lims.api.auth.service.TokenProvider;
import lims.api.auth.session.TokenSession;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Authenticator {

    private final TokenProvider tokenProvider;
    private final TokenAuthenticationConfigurer authenticationConfigure;
    private final JwtResolver jwtResolver;

    public TokenAuthentication authenticate(AccountAuthentication authenticationToLogin) {
        if (Strings.isEmpty(authenticationToLogin.getPlntCd()) ||
            Strings.isEmpty(authenticationToLogin.getUsername()) ||
            Strings.isEmpty(authenticationToLogin.getPassword())) {
            throw new UnauthenticatedException("auth.error.notFoundAuthorization");
        }

        boolean isAuthenticated = authenticationConfigure.authenticate(authenticationToLogin);
        if (!isAuthenticated) {
            throw new UnauthenticatedException("auth.error.invalidAccount");
        }

        return authenticate(authenticationToLogin.toSafeAuthentication());
    }

    public TokenAuthentication authenticate(SafeAccountAuthentication authentication) {
        String username = authentication.getUsername();
        Claims customClaims = getCustomClaims(authentication);
        setUserNameToClaimForSession(customClaims, username);
        return createAuthentication(customClaims);
    }

    public TokenAuthentication reauthenticate(String jwt) {
        try {
            if (jwt == null) {
                throw new UnauthenticatedException("auth.error.notFoundAuthorization");
            }
            return createAuthentication(jwtResolver.getCustomClaims(jwt));
        } catch (UnauthenticatedException e) {
            log.error("[{}] Failed to reauthenticate by token. {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("[{}] Throw Exception during reauthenticate by token. {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
            throw e;
        }
    }

    public boolean verify(String jwt) {
        return tokenProvider.verify(jwt);
    }

    private TokenAuthentication createAuthentication(Claims customClaims) {
        Token accessToken = tokenProvider.generateAccessToken(customClaims);
        Token refreshToken = tokenProvider.generateRefreshToken(customClaims);
        Claims jwtCustomClaims = jwtResolver.getCustomClaims(accessToken.getJwt());
        return new TokenAuthentication(accessToken, refreshToken, jwtCustomClaims);
    }

    public Claims getCustomClaims(SafeAccountAuthentication authentication) {
        Claims claims = authenticationConfigure.createCustomClaimsOnAuthenticateSuccess(authentication);
        return claims == null ? new Claims() : claims;
    }

    private void setUserNameToClaimForSession(Map<String, String> customClaims, String username) {
        customClaims.put(TokenSession.SESSION_KEY_IN_CLAIMS, username);
    }

}