package lims.api.auth.service;

import lims.api.auth.domain.DecodedToken;
import lims.api.auth.domain.Token;

import java.util.Map;

public interface TokenProvider {

    Token generateAccessToken(Map<String, String> customClaims);

    Token generateRefreshToken(Map<String, String> customClaims);

    boolean verify(String jsw);

    DecodedToken decode(String jsw);

}