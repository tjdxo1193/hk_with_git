package lims.api.auth.properties;

import lims.api.auth.properties.domain.ExpireProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "auth.token.refresh-token.expire")
public class RefreshTokenExpireProperties extends ExpireProperty {

    public RefreshTokenExpireProperties(Long days, Long hours, Long minutes, Long seconds) {
        super(days, hours, minutes, seconds);
    }
}