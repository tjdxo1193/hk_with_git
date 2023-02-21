package lims.api.common.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.common.entity.SyAprInfo;
import lims.api.common.vo.ApproveVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

@Mapper
@Repository
public interface ApproveDao {

    @Audit(target = SyAprInfo.class, label = AuditEvent.Approve.create)
    int create(ApproveVO param);

    @Audit(target = SyAprInfo.class, label = AuditEvent.Approve.update)
    int update(ApproveVO param);

    @Audit(target = SyAprInfo.class, label = AuditEvent.Approve.approve)
    int approve(ApproveVO param);
}