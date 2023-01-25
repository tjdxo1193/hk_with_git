package lims.api.auth.model;

import lims.api.auth.domain.AccountAuthentication;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthenticationRequest {

    @NotBlank
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