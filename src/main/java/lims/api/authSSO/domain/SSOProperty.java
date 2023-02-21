package lims.api.authSSO.domain;

import lims.api.util.StringUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Getter
@Component
@PropertySource("classpath:interface-system/${spring.profiles.active}.properties")
public class SSOProperty {

    @Value("${sso.idp.url}")
    private String SSOUrl;

    @Value("${sso.idp.client.id}")
    private String SSOClientId;

    @Value("${sso.idp.cert.key}")
    private String SSOCertKey;

    @Value("${sso.redirect.url.after.sso.login}")
    private String redirectUrlAfterSSOLogin;

    @Value("${sso.redirect.url.after.sso.authenticate}")
    private String redirectUrlAfterSSOAuthenticate;

    public String getSSOLoginPageUrl() {
        return SSOUrl
                + "?client_id=" + SSOClientId
                + "&redirect_uri=" + redirectUrlAfterSSOLogin
                + "&response_mode=form_post"
                + "&response_type=code+id_token"
                + "&scope=openid+profile"
                + "&nonce=" + StringUtil.generateUUID(22);
    }

    public String getRedirectUrlAfterSSOAuthenticate(Map<String, String> queryParam) {
        return makeRedirectUrlWithQueryParam(convertToQueryString(queryParam));
    }

    private String makeRedirectUrlWithQueryParam(String queryString) {
        return redirectUrlAfterSSOAuthenticate + "?" + queryString;
    }

    private String convertToQueryString(Map<String, String> queryParam) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : queryParam.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8),
                    URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)
            ));
        }
        return builder.toString();
    }

}