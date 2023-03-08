package lims.api.common.dao;

import lims.api.auth.domain.SafeAccountAuthentication;
import lims.api.common.vo.AuthorityVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AuthorizationDao {

    AuthorityVO findAuthorityByUserLoginId(SafeAccountAuthentication param);

    List<String> findMenuByLoginId(SafeAccountAuthentication param);

    boolean hasAuthMenu(@Param("userId") String userId, @Param("menuCd") String menuCd);

}