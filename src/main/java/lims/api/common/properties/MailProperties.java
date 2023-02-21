package lims.api.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail")
@RequiredArgsConstructor
public class MailProperties {
    private final String host;
    private final String port;
    private final String username;
    private final String password;
}