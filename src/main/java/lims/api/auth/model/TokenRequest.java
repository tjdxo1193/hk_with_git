package lims.api.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TokenRequest {
    @NotEmpty
    private String refreshToken;
}
