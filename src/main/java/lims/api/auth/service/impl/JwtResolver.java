package lims.api.auth.service.impl;

import lims.api.auth.domain.Claims;
import lims.api.auth.domain.DecodedToken;
import lims.api.auth.service.TokenProvider;
import lims.api.auth.session.TokenSession;
import lims.api.common.domain.TokenClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtResolver {

    private final TokenProvider tokenProvider;

    public Claims getCustomClaims(String jwt) {
        DecodedToken decodedToken = Optional.ofNullable(tokenProvider.decode(jwt)).orElse(new DecodedToken());
        return decodedToken.getClaims();
    }

    public String getCustomClaim(String jwt, String claimName) {
        DecodedToken decodedToken = Optional.ofNullable(tokenProvider.decode(jwt)).orElse(new DecodedToken());
        return Optional.ofNullable(decodedToken.getClaims()).orElse(new Claims()).get(claimName);
    }

    public String getUsername(String jwt) {
        return getCustomClaim(jwt, TokenSession.SESSION_KEY_IN_CLAIMS);
    }

    public String getUserId(String jwt) {
        return getCustomClaim(jwt, TokenClaims.CLAIM_NAME_USER_ID);
    }

    public String getLoginId(String jwt) {
        return getCustomClaim(jwt, TokenClaims.CLAIM_NAME_LOGIN_ID);
    }

    public String getEmId(String jwt) {
        return getCustomClaim(jwt, TokenClaims.CLAIM_NAME_EM_ID);
    }

    public String getDptCd(String jwt) {
        return getCustomClaim(jwt, TokenClaims.CLAIM_NAME_DPT_CODE);
    }

    public String getUserNm(String jwt) {
        return getCustomClaim(jwt, TokenClaims.CLAIM_NAME_USER_NM);
    }

    public String getPlantCode(String jwt) {
        return getCustomClaim(jwt, TokenClaims.CLAIM_NAME_PLANT_CODE);
    }

    public boolean isExpiresAtInfinitely(String jwt) {
        DecodedToken decodedToken = Optional.ofNullable(tokenProvider.decode(jwt)).orElse(new DecodedToken());
        return decodedToken.getJwt() != null && decodedToken.getExpiresAt() == null;
    }

}