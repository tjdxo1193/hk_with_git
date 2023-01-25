package lims.api.auth.service.impl;

import lims.api.auth.condition.CookieTokenStrategyCondition;
import lims.api.auth.domain.Token;
import lims.api.auth.domain.TokenAuthentication;
import lims.api.auth.model.TokenResponse;
import lims.api.auth.properties.AuthProperties;
import lims.api.auth.properties.CookieProperties;
import lims.api.auth.properties.domain.TokenProperty;
import lims.api.auth.service.TokenHttpResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Component
@Conditional(CookieTokenStrategyCondition.class)
@RequiredArgsConstructor
public class CookieTokenHttpResolver implements TokenHttpResolver {

    private final AuthProperties properties;

    private String getAccessTokenName() {
        return properties.getAccessToken().getName();
    }

    @Override
    public Token getAccessToken() {
        return new Token(findTokenInCookies(getRequest().getCookies(), getAccessTokenName()));
    }

    private String findTokenInCookies(Cookie[] cookies, String cookieName) {
        return Arrays.stream(Optional.ofNullable(cookies).orElseGet(() -> new Cookie[0]))
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny()
                .orElse(null);
    }

    @Override
    public HttpHeaders makeResponseHeader(TokenAuthentication authentication) {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseCookie accessTokenCookie = makeResponseCookie(properties.getAccessToken(), authentication.getAccessToken());
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        return httpHeaders;
    }

    private ResponseCookie makeResponseCookie(TokenProperty tokenProperty, Token token) {
        CookieProperties cookieProperties = properties.getCookie();
        return ResponseCookie
                .from(getAccessTokenName(), token.getJwt())
                .maxAge(tokenProperty.getExpire().getMaxAge())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite().getValue())
                .httpOnly(cookieProperties.isHttpOnly())
                .path(cookieProperties.getPath())
                .build();
    }

    @Override
    public TokenResponse makeResponseBody(TokenAuthentication authentication) {
        return TokenResponse.builder()
                .refreshToken(authentication.getRefreshToken().getJwt())
                .claims(authentication.getClaims())
                .build();
    }

    private HttpServletRequest getRequest() {
        return HttpHelper.getHttpServletRequest();
    }

}