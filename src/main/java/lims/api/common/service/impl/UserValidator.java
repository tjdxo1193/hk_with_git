package lims.api.common.service.impl;

import lims.api.auth.exception.UnauthenticatedException;
import lims.api.common.dao.UserDao;
import lims.api.common.vo.UserVO;
import lims.api.util.crypto.BcryptCrypto;
import lims.api.util.crypto.OneWayCrypto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    public static final int LOGIN_FAIL_LIMIT_COUNT = 5;
    private final OneWayCrypto crypto = new BcryptCrypto();
    private final UserDao userDao;

    public boolean validate(String plntCd, String username, String password) {
        UserVO userInfo = getUserInfo(plntCd, username);

        boolean noExistsUser = userInfo == null;
        if (noExistsUser) {
            return false;
        }

//        if (isLockedUser(username)) {
//            throw new UnauthenticatedException("auth.error.userInfoLocked");
//        }

        if (isLockedUser(userInfo)) {
            throw new UnauthenticatedException("auth.error.userInfoLocked");
        }

        if (isEqualsPassword(password, userInfo.getPassword())) {
            return true;
        }

        updateLognFailCnt(userInfo);
        return false;
    }

    private UserVO getUserInfo(String plntCd, String username) {
        UserVO loginUser = new UserVO();
        loginUser.setPlntCd(plntCd);
        loginUser.setUserLognId(username);
        return userDao.findByUserLoginIdAndPlantCode(loginUser);
    }

    private boolean isEqualsPassword(String password, String encryptedPassword) {
        return crypto.equals(password, encryptedPassword);
    }

    private void updateLognFailCnt(UserVO user) {
        userDao.updateLognFailCnt(user);

        int lognFailCnt = user.getLognFailCnt();
        if (lognFailCnt == LOGIN_FAIL_LIMIT_COUNT) {
            user.setLockYn("Y");
            userDao.updateLockYn(user);
        }
    }

//    private boolean isLockedUser(String username) {
//        UserVO userInfo = userDao.findByUserLoginId(username);
//        return !StringUtils.isEmpty(userInfo.getUserId()) && StringUtils.equals(userInfo.getLockYn(), "Y");
//    }

    private boolean isLockedUser(UserVO user) {
        UserVO userInfo = userDao.findByUserLoginIdAndPlantCode(user);
        return !StringUtils.isEmpty(userInfo.getUserId()) && StringUtils.equals(userInfo.getLockYn(), "Y");
    }

}