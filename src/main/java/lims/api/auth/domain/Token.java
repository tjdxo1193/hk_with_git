package lims.api.auth.domain;

public class Token {

    private String jwt = null;

    public Token() {}

    public Token(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt(){
        return jwt;
    }

}