package lims.api.st.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.st.vo.StabSampleUsageVO;
import lims.api.tp.entity.QsSmpUse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface StabSampleUsageDao {
    List<StabSampleUsageVO> find(StabSampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.requestApproveUse)
    int requestApproveUse(StabSampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.requestCancelUse)
    int requestCancelUse(StabSampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.createSampleUsage)
    int create(StabSampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.updateSampleUsage)
    int update(StabSampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.deleteSampleUsage)
    int delete(StabSampleUsageVO param);

    List<StabSampleUsageVO> findSample(StabSampleUsageVO param);
}
