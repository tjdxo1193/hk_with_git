package lims.api.auth.properties;

import lims.api.auth.enums.CookieSameSite;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "auth.token.cookie")
public class CookieProperties {
    private final String defaultPath = "/";
    private final boolean secure;
    private final boolean httpOnly;
    private final CookieSameSite sameSite;
    private final String path;

    public CookieProperties(Boolean secure, Boolean httpOnly, CookieSameSite sameSite, String path) {
        this.secure = secure != null && secure;
        this.httpOnly = httpOnly == null || httpOnly;
        this.sameSite = sameSite == null ? CookieSameSite.STRICT : sameSite;
        this.path = path == null ? defaultPath : path;
    }
}