package lims.api.common.dao;

import lims.api.common.vo.AuthorityVO;
import lims.api.sy.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AuthorizationDao {

    AuthorityVO findAuthorityByUserLoginId(String loginId);

    List<String> findMenuByLoginId(String userId);

    boolean hasAuthMenu(@Param("userId") String userId, @Param("menuCd") String menuCd);

}