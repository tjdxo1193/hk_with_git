package lims.api.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountAuthentication {
    private String plntCd;
    private String username;
    private String password;

    public SafeAccountAuthentication toSafeAuthentication() {
        return SafeAccountAuthentication.builder()
                .plntCd(plntCd)
                .username(username)
                .build();
    }

}