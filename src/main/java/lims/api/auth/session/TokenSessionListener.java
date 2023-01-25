package lims.api.auth.session;

import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.auth.properties.AuthProperties;
import lims.api.auth.service.TokenAuthenticationConfigurer;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

@Slf4j
public class TokenSessionListener implements HttpSessionListener {

    private final Long sessionMaxInactiveTime;
    private final JwtResolver jwtResolver;
    private final TokenAuthenticationConfigurer authenticationConfigurer;

    public TokenSessionListener(AuthProperties properties, JwtResolver jwtResolver, TokenAuthenticationConfigurer authenticationConfigurer) {
        this.sessionMaxInactiveTime = properties.getTokenSessionMaxInactiveTime();
        this.jwtResolver = jwtResolver;
        this.authenticationConfigurer = authenticationConfigurer;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String jwt = null;

        try {
            HttpSession session = se.getSession();

            if (!isTokenSession(session)) {
                return;
            }

            Enumeration<String> attributes = session.getAttributeNames();

            while (attributes.hasMoreElements()) {
                String attribute = attributes.nextElement();

                if (isAttributeNameOfUsernameClaims(attribute)) {
                    jwt = String.valueOf(session.getAttribute(attribute));

                    if (jwt == null) {
                        return;
                    }

                    SafeAccountAuthentication authentication = SafeAccountAuthentication.builder()
                            .plntCd(jwtResolver.getPlantCode(jwt))
                            .username(jwtResolver.getLoginId(jwt))
                            .build();
                    authenticationConfigurer.onLogout(authentication);
                    return;
                }
            }
        } catch (Exception e) {
            log.error("[{}] Can't listen destroy session. jwt: {}", ThreadUtil.getCurrentMethodName(), jwt);
            log.error(e.getMessage());
        }
    }

    private boolean isTokenSession(HttpSession session) {
        return session != null && Integer.valueOf(session.getMaxInactiveInterval()).longValue() == sessionMaxInactiveTime;
    }

    private boolean isAttributeNameOfUsernameClaims(String attribute) {
        return attribute.contains(TokenSession.SESSION_KEY_SUFFIX);
    }
}