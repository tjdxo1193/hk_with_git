package lims.api.common.service.impl;

import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.common.dao.AuthorizationDao;
import lims.api.common.service.AuthorizationService;
import lims.api.common.vo.AuthorityVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizationDao authorizationDao;

    @Override
    public AuthorityVO getAuthorityByLoginId(String plantCode, String loginId) {
        return authorizationDao.findAuthorityByUserLoginId(createParam(plantCode, loginId));
    }

    @Override
    public List<String> getMyMenuCodesByLoginId(String plantCode, String loginId) {
        return authorizationDao.findMenuByLoginId(createParam(plantCode, loginId));
    }

    @Override
    public boolean isAllowedMenu(String userId, String menuCode) {
        return authorizationDao.hasAuthMenu(userId, menuCode);
    }

    private SafeAccountAuthentication createParam(String plantCode, String loginId) {
        return SafeAccountAuthentication.builder()
                .plntCd(plantCode)
                .username(loginId)
                .build();
    }
}