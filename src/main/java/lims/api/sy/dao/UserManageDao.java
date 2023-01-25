package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.UserManageEntity;
import lims.api.sy.vo.UserManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface UserManageDao {

    List<UserManageVO> findAll(UserManageVO vo);

    @Audit(target = UserManageEntity.class, label = AuditEvent.User.update)
    int update(UserManageVO vo);
    @Audit(target = UserManageEntity.class, label = AuditEvent.User.initPwd)
    int initPwd(UserManageVO vo);
}