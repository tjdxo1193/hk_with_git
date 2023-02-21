package lims.api.auth.domain;

import com.google.gson.Gson;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TokenAuthentication {
    private final Token accessToken;
    private final Token refreshToken;
    private final Claims claims;

    public TokenAuthentication(Token accessToken, Token refreshToken, Claims claims) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.claims = claims;
    }

    public Map<String, String> toRedirectAttributes() {
        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken.getJwt());
        map.put("refreshToken", refreshToken.getJwt());
        map.put("claims", new Gson().toJson(claims));
        return map;
    }
}