package lims.api.common.service;

import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.common.vo.LoginAuditRecordVO;
import lims.api.common.vo.UserVO;

import java.util.List;

public interface LoginService {
    List<LoginAuditRecordVO> getLoginAuditList(LoginAuditRecordVO param);
    void loginSucceseAudit(SafeAccountAuthentication authentication);
    void loginFailAudit(SafeAccountAuthentication authentication);
    void logout(SafeAccountAuthentication authentication);
    UserVO getLoginUserInfo(UserVO user);
}