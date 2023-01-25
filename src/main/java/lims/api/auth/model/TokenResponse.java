package lims.api.auth.model;

import lims.api.auth.domain.Claims;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private Claims claims;
}