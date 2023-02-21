package lims.api.common.service.impl;

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
    public AuthorityVO getAuthorityByLoginId(String loginId) {
        return authorizationDao.findAuthorityByUserLoginId(loginId);
    }

    @Override
    public List<String> getMyMenuCodesByLoginId(String userId) {
        return authorizationDao.findMenuByLoginId(userId);
    }

    @Override
    public boolean isAllowedMenu(String userId, String menuCode) {
        return authorizationDao.hasAuthMenu(userId, menuCode);
    }
}