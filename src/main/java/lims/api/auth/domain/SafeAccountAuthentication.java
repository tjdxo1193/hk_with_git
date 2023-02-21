package lims.api.auth.domain;

import lims.api.common.enums.UseType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SafeAccountAuthentication {
    private String plntCd;
    private String username;
    private UseType sso = UseType.N;

}