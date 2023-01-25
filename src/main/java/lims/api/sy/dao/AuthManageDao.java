package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.SyAth;
import lims.api.sy.entity.SyAthGp;
import lims.api.sy.vo.AuthManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Repository
@Mapper
public interface AuthManageDao {
    List<AuthManageVO> findAll(AuthManageVO param);

    @Audit(target = SyAth.class, label = AuditEvent.Auth.update)
    int update(AuthManageVO param);

    @Audit(target = SyAth.class, label = AuditEvent.Auth.create)
    int insert(AuthManageVO param);

    List<AuthManageVO> findNotAthGp(AuthManageVO param);

    List<AuthManageVO> findAthGp(AuthManageVO param);

    @Audit(target = SyAthGp.class, label = AuditEvent.Auth.delete)
    int deleteAthGp(AuthManageVO param);

    @Audit(target = SyAthGp.class, label = AuditEvent.Auth.create)
    int insertAthGp(AuthManageVO param);
}
