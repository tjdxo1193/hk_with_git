package lims.api.common.service.impl;

import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.auth.exception.UnauthenticatedException;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.common.dao.LoginDao;
import lims.api.common.dao.UserDao;
import lims.api.common.enums.LoginStatus;
import lims.api.common.enums.UseType;
import lims.api.common.service.AuthorizationService;
import lims.api.common.service.LoginService;
import lims.api.common.vo.AuthorityVO;
import lims.api.common.vo.LoginAuditRecordVO;
import lims.api.common.vo.UserVO;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final String[] superUserLoginIds = { "admin" };

    private final LoginDao loginDao;
    private final UserDao userDao;
    private final AuthorizationService authorizationService;

    @Override
    public List<LoginAuditRecordVO> getLoginAuditList(LoginAuditRecordVO param) {
        return loginDao.getLoginAuditList(param);
    }

    @Override
    public void loginSucceseAudit(SafeAccountAuthentication authentication) {
        LoginAuditRecordVO param = getUserInfoByUserLoginId(authentication.getPlntCd(), authentication.getUsername(), authentication.getSso());
        param.setConectSe(LoginStatus.LOGIN.getValue());

        loginDao.insertLoginAudit(param);
    }

    @Override
    public void loginFailAudit(SafeAccountAuthentication authentication) {
        LoginAuditRecordVO param = getUserInfoByUserLoginId(authentication.getPlntCd(), authentication.getUsername(), authentication.getSso());
        param.setConectSe(LoginStatus.FAIL.getValue());

        loginDao.insertLoginAudit(param);

        // TODO 실패 횟수가 ~번 이상일 때, 계정 잠금.
    }

    @Override
    public void logout(SafeAccountAuthentication authentication) {
        LoginAuditRecordVO param = getUserInfoByUserLoginId(authentication.getPlntCd(), authentication.getUsername(), authentication.getSso());
        param.setConectSe(LoginStatus.LOGOUT.getValue());

        loginDao.insertLoginAudit(param);
    }

    @Override
    public UserVO getLoginUserInfo(UserVO userVO) {
        String plantCode = userVO.getPlntCd();
        String loginId = userVO.getUserLognId();

        UserVO user = Optional.ofNullable(userDao.getLoginUserInfo(userVO)).orElse(null);
        if (user == null) {
            log.error("[{}] Not found account.", ThreadUtil.getCurrentMethodName());
            throw new UnauthenticatedException("auth.error.invalidAccount");
        }

        AuthorityVO authority = authorizationService.getAuthorityByLoginId(plantCode, loginId);
        if (hasAuthority(authority)) {
            authority.setMyMenus(authorizationService.getMyMenuCodesByLoginId(plantCode, loginId));
        }
        if (isSuperUser(user.getUserLognId())) {
            user.setSuperUser(true);
        }
        user.setAuthority(authority);
        return user;
    }

    private boolean hasAuthority(AuthorityVO authority) {
        return authority != null;
    }

    private boolean isSuperUser(String loginId) {
        return PatternMatchUtils.simpleMatch(superUserLoginIds, loginId);
    }

    private LoginAuditRecordVO getUserInfoByUserLoginId(String plntCd, String loginId, UseType sso) {
        String userIp = HttpHelper.getClientIp();

        LoginAuditRecordVO loginUserInfo = new LoginAuditRecordVO();
        loginUserInfo.setPlntCd(plntCd);
        loginUserInfo.setConectUid(loginId);
        loginUserInfo.setSso(sso);
        loginUserInfo.setConectIp(userIp);

        return loginUserInfo;
    }
}