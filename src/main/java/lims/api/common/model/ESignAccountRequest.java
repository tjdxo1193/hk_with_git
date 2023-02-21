package lims.api.common.model;

import lims.api.auth.domain.AccountAuthentication;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ESignAccountRequest {

    private String plntCd;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public AccountAuthentication toAuthentication() {
        return AccountAuthentication.builder()
                .plntCd(plntCd)
                .username(username)
                .password(password)
                .build();
    }
}