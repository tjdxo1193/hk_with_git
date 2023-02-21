package lims.api.auth.config;

import lims.api.auth.properties.AuthProperties;
import lims.api.auth.resolver.TokenArgumentResolver;
import lims.api.auth.service.TokenAuthenticationConfigurer;
import lims.api.auth.service.impl.Authenticator;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.auth.session.TokenSessionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionListener;

@Configuration
@RequiredArgsConstructor
public class TokenSessionConfig {

    private final AuthProperties properties;
    private final JwtResolver jwtResolver;
    private final TokenAuthenticationConfigurer authenticationConfigurer;

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new TokenSessionListener(properties, jwtResolver, authenticationConfigurer));
    }

}