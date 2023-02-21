package lims.api.auth.properties;

import lims.api.auth.properties.domain.ExpireProperty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "auth.token.access-token.expire")
public class AccessTokenExpireProperties extends ExpireProperty {

    public AccessTokenExpireProperties(Long days, Long hours, Long minutes, Long seconds) {
        super(days, hours, minutes, seconds);
    }
}