package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.common.entity.SyAprInfo;
import lims.api.ms.entity.MmMitmSpec;
import lims.api.ms.entity.MmMitmSpecAitm;
import lims.api.ms.vo.MonitorSpecApprRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;
@Mapper
@Repository
public interface MonitorSpecApprRequestDao {

    List<MonitorSpecApprRequestVO> getVersionList(MonitorSpecApprRequestVO param);

    List<MonitorSpecApprRequestVO> getAItemList(MonitorSpecApprRequestVO param);

    @Audit(target = MmMitmSpec.class, label = AuditEvent.MonitorSpec.updateApproveRequest)
    int updateApprovalRequest(MonitorSpecApprRequestVO param);

    @Audit(target = MmMitmSpec.class, label = AuditEvent.MonitorSpec.updateApproveRequestReject)
    int updateReviewReturn(MonitorSpecApprRequestVO param);

}
