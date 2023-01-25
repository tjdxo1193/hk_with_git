package lims.api.auth.service.impl;

import lims.api.auth.service.TokenAuthenticationConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(value = TokenAuthenticationConfigurer.class)
public class DefaultTokenAuthenticationConfigure implements TokenAuthenticationConfigurer {
}
