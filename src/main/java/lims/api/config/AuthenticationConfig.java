package lims.api.config;

import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.auth.domain.AccountAuthentication;
import lims.api.auth.domain.Claims;
import lims.api.auth.service.TokenAuthenticationConfigurer;
import lims.api.common.domain.TokenClaims;
import lims.api.common.service.LoginService;
import lims.api.common.service.impl.UserValidator;
import lims.api.common.vo.ClaimsVO;
import lims.api.common.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements TokenAuthenticationConfigurer {

    private final LoginService loginService;
    private final UserValidator userValidator;
    private final TokenClaims tokenClaims;

    /**
     * 토큰 발급 전 사용자 정보를 검증합니다.
     * true를 반환하면 토큰을 발급합니다.
     */
    @Override
    public boolean authenticate(AccountAuthentication authentication) {
        String plntCd = authentication.getPlntCd();
        String username = authentication.getUsername();
        String password = authentication.getPassword();
        return userValidator.validate(plntCd, username, password);
    }

    /**
     * authenticate 메서드가 true를 반환하고 토큰이 발급될 때
     * 해당 토큰에 저장될 claims를 구성합니다.
     */
    @Override
    public Claims createCustomClaimsOnAuthenticateSuccess(SafeAccountAuthentication authentication) {
        UserVO userInfo = new UserVO();
        userInfo.setPlntCd(authentication.getPlntCd());
        userInfo.setUserLognId(authentication.getUsername());
        UserVO user = loginService.getLoginUserInfo(userInfo);

        return tokenClaims.create(ClaimsVO.builder()
                .userId(user.getUserId())
                .loginId(authentication.getUsername())
                .emId(user.getEmid())
                .plntCd(user.getPlntCd())
                .dptCd(user.getDptCd())
                .userNm(user.getUserNm())
                .authority(user.getAuthority())
                .superUser(user.isSuperUser())
                .build());
    }

    /**
     * 로그인 성공 후 호출됩니다.
     */
    @Override
    public void onLogin(SafeAccountAuthentication authentication) {
        loginService.loginSucceseAudit(authentication);
    }

    /**
     * 로그인이 실패하거나 도중에 예외가 발생했을 때 실행됩니다
     */
    @Override
    public void onLoginFailed(SafeAccountAuthentication authentication) {
        loginService.loginFailAudit(authentication);
    }

    /**
     * 토큰 만료 요청(/auth/token/revocation)이 처리되거나 토큰 세션이 만료되었을 때 실행됩니다.
     */
    @Override
    public void onLogout(SafeAccountAuthentication authentication) {
        /**
         * TODO
         *  logout 시 ADFS 로그아웃이 필요하면 ssoProperty에 다음 url을 추가하고 여기서 호출하도록 설정
         *  https://sts.kolmar.co.kr/adfs/ls/?wa=wsignoutcleanup1.0
         */
        loginService.logout(authentication);
    }

}