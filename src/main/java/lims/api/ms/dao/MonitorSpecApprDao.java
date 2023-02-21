package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.MmMitmSpec;
import lims.api.ms.vo.MonitorSpecApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;
@Mapper
@Repository
public interface MonitorSpecApprDao {
    List<MonitorSpecApprVO> getVersionList(MonitorSpecApprVO param);

    List<MonitorSpecApprVO> getAItmList(MonitorSpecApprVO param);
    @Audit(target = MmMitmSpec.class, label = AuditEvent.MonitorSpec.updateApproved)
    int updateApproval(MonitorSpecApprVO mvo);

    @Audit(target = MmMitmSpec.class, label = AuditEvent.MonitorSpec.updateApprovedReject)
    int updateReject(MonitorSpecApprVO mvo);
}
