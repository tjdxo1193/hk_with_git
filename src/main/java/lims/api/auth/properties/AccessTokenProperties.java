package lims.api.auth.properties;

import lims.api.auth.properties.domain.TokenProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "auth.token.access-token")
public class AccessTokenProperties extends TokenProperty {

    public AccessTokenProperties(AccessTokenExpireProperties expire) {
        super(expire);
    }

    @Override
    public String getName() {
        return "accessToken";
    }

    @Override
    protected AccessTokenExpireProperties getDefaultExpire() {
        return new AccessTokenExpireProperties(null, null, 30L, null);
    }
}