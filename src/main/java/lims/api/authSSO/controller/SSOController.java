package lims.api.authSSO.controller;

import lims.api.auth.annotation.Permit;
import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.auth.domain.Token;
import lims.api.auth.domain.TokenAuthentication;
import lims.api.auth.properties.AuthProperties;
import lims.api.auth.service.TokenAuthenticationConfigurer;
import lims.api.auth.service.impl.Authenticator;
import lims.api.auth.session.TokenSession;
import lims.api.authSSO.domain.SSOProperty;
import lims.api.authSSO.model.SSOLoginRequest;
import lims.api.authSSO.service.impl.SSOJwtResolver;
import lims.api.common.dao.UserDao;
import lims.api.common.enums.UseType;
import lims.api.common.vo.UserVO;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("${apiPrefix}/sso")
@RequiredArgsConstructor
public class SSOController {

    private final AuthProperties authProperties;
    private final SSOProperty ssoProperty;
    private final SSOJwtResolver ssoJwtResolver;
    private final Authenticator authenticator;
    private final TokenAuthenticationConfigurer authenticationConfigure;
    private final UserDao userDao;

    @Permit
    @GetMapping(value = "/login")
    public ResponseEntity<String> toSSOLoginPage() {
        String ssoLoginPageUrl = ssoProperty.getSSOLoginPageUrl();
        return ResponseEntity.ok(ssoLoginPageUrl);
    }

    @Permit
    @PostMapping(value = "/authentication", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> SSOAuthenticate(SSOLoginRequest request, HttpSession session, RedirectAttributes redirectAttr) {
        String plantCode = null;
        String loginId = null;
        try {
            String jwt = request.getIdToken();
            loginId = ssoJwtResolver.getLoginId(jwt);

            UserVO user = userDao.findByUserLoginId(loginId);
            plantCode = user.getPlntCd();

            assertExistsPlantCode(plantCode);
            assertExistsLoginId(loginId);

            SafeAccountAuthentication safeAccountAuthentication = SafeAccountAuthentication.builder()
                    .plntCd(plantCode)
                    .username(loginId)
                    .sso(UseType.Y)
                    .build();
            TokenAuthentication tokenAuthentication = authenticator.authenticated(safeAccountAuthentication);

            Token refreshToken = tokenAuthentication.getRefreshToken();
            setRefreshTokenToSession(session, safeAccountAuthentication.getUsername(), refreshToken.getJwt());

            authenticationConfigure.onLogin(safeAccountAuthentication);

            HttpHeaders headers = new HttpHeaders();
            Map<String, String> queryParam = tokenAuthentication.toRedirectAttributes();
            headers.add("location", ssoProperty.getRedirectUrlAfterSSOAuthenticate(queryParam));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        } catch (Exception e) {
            log.error("[{}] sso id token: {}. \n {}", ThreadUtil.getCurrentMethodName(), request.getIdToken(), e);
            if (StringUtils.isNotEmpty(plantCode) && StringUtils.isNotEmpty(loginId)) {
                SafeAccountAuthentication authentication = SafeAccountAuthentication
                        .builder()
                        .plntCd(plantCode)
                        .username(loginId)
                        .sso(UseType.Y)
                        .build();
                authenticationConfigure.onLoginFailed(authentication);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("location", ssoProperty.getRedirectUrlAfterSSOAuthenticate());
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }
    }

    private void assertExistsPlantCode(String plantCode) {

    }

    private void assertExistsLoginId(String logindId) {

    }

    private void setRefreshTokenToSession(HttpSession session, String key, String refreshToken) {
        TokenSession.set(session, key, refreshToken, authProperties.getTokenSessionMaxInactiveTime());
    }

}