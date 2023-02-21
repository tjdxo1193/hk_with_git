package lims.api.authSSO.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SSOLoginRequest {
    private String code;
    @Getter(AccessLevel.NONE)
    private String id_token;

    public String getIdToken() {
        return id_token;
    }
}