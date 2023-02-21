package lims.api.authSSO.domain;

import lims.api.auth.domain.Claims;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SSOTokenClaims {

    public static final String CLAIM_NAME_LOGIN_ID = "LOGINID";

    public Claims create(Map<String, Object> param) {
        Claims claims = new Claims();
        claims.put(CLAIM_NAME_LOGIN_ID, toValue(param, CLAIM_NAME_LOGIN_ID));
        return claims;
    }

    private String toValue(Map<String, Object> map, String key) {
        return String.valueOf(map.get(key));
    }

}