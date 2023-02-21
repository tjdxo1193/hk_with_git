package lims.api.common.service;

import lims.api.common.vo.AuthorityVO;

import java.util.List;

public interface AuthorizationService {

    AuthorityVO getAuthorityByLoginId(String loginId);

    List<String> getMyMenuCodesByLoginId(String userId);

    boolean isAllowedMenu(String userId, String menuCode);

}