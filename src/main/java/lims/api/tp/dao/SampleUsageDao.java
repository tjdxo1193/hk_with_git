package lims.api.tp.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.tp.entity.QsSmpUse;
import lims.api.tp.vo.SampleUsageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface SampleUsageDao {
    List<SampleUsageVO> find(SampleUsageVO param);

    List<SampleUsageVO> findSample(SampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.createSampleUsage, commandType = CommandType.INSERT)
    int create(SampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.updateSampleUsage, commandType = CommandType.UPDATE)
    int update(SampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.deleteSampleUsage, commandType = CommandType.DELETE)
    int delete(SampleUsageVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.requestApproveUse)
    int requestApprove(SampleUsageVO param);
}
