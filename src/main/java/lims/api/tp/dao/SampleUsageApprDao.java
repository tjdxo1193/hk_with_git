package lims.api.tp.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.tp.entity.QsSmpUse;
import lims.api.tp.vo.SampleUsageApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface SampleUsageApprDao {
    List<SampleUsageApprVO> find(SampleUsageApprVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.approve)
    int approve(SampleUsageApprVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.reject)
    int reject(SampleUsageApprVO param);
}
