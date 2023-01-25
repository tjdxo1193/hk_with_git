package lims.api.auth.service;

import lims.api.auth.domain.Token;
import lims.api.auth.domain.TokenAuthentication;
import lims.api.auth.model.TokenResponse;
import org.springframework.http.HttpHeaders;

public interface TokenHttpResolver {

    Token getAccessToken();

    HttpHeaders makeResponseHeader(TokenAuthentication authentication);

    TokenResponse makeResponseBody(TokenAuthentication authentication);

}