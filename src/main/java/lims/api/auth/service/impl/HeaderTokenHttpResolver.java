package lims.api.auth.service.impl;

import lims.api.auth.condition.DefaultOrHeaderTokenStrategyCondition;
import lims.api.auth.domain.Token;
import lims.api.auth.domain.TokenAuthentication;
import lims.api.auth.model.TokenResponse;
import lims.api.auth.service.TokenHttpResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@Slf4j
@Conditional(DefaultOrHeaderTokenStrategyCondition.class)
public class HeaderTokenHttpResolver implements TokenHttpResolver {

    @Override
    public Token getAccessToken() {
        String bearerToken = Optional.ofNullable(getRequest().getHeader("authorization")).orElse("");
        String jwt = bearerToken.replace("Bearer", "").trim();
        return new Token(jwt);
    }

    @Override
    public HttpHeaders makeResponseHeader(TokenAuthentication authentication) {
        return new HttpHeaders();
    }

    @Override
    public TokenResponse makeResponseBody(TokenAuthentication authentication) {
        return TokenResponse.builder()
                .accessToken(authentication.getAccessToken().getJwt())
                .refreshToken(authentication.getRefreshToken().getJwt())
                .claims(authentication.getClaims())
                .build();
    }

    private HttpServletRequest getRequest() {
        return HttpHelper.getHttpServletRequest();
    }

}