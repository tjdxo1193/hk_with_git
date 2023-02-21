package lims.api.auth.domain;

import org.springframework.util.StringUtils;

public class Token {

    private String jwt = null;

    public Token() {}

    public Token(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt(){
        return jwt;
    }

    public boolean isEmpty() {
        return !StringUtils.hasLength(jwt);
    }

}