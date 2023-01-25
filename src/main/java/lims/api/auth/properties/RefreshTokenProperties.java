package lims.api.auth.properties;

import lims.api.auth.properties.domain.ExpireProperty;
import lims.api.auth.properties.domain.TokenProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "auth.token.refresh-token")
public class RefreshTokenProperties extends TokenProperty {

    public RefreshTokenProperties(ExpireProperty expire) {
        super(expire);
    }

    @Override
    public String getName() {
        return "refreshToken";
    }

    @Override
    protected RefreshTokenExpireProperties getDefaultExpire() {
        return new RefreshTokenExpireProperties(null, 1L, null, null);
    }
}