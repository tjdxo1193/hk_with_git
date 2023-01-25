package lims.api.common.dao;

import lims.api.common.vo.UserVO;
import lims.api.ms.vo.UserDelegateVO;
import lims.api.sy.vo.PwdChangeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {

    UserVO getLoginUserInfo(UserVO user);

    UserVO findByUserLoginId(String loginId);

    UserVO findByUserLoginIdAndPlantCode(UserVO user);

    int updateLognFailCnt(UserVO user);

    int updateLockYn(UserVO user);

    List<UserVO> findDelegateAssignUserIdsByUserId(UserDelegateVO param);


    PwdChangeVO getUserInfoToPwdChange(PwdChangeVO param);

    int pwdChange(PwdChangeVO param);

    String getPwd(PwdChangeVO param);
}