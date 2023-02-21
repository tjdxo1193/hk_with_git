package lims.api.st.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.st.vo.StabSampleUsageApprVO;
import lims.api.tp.entity.QsSmpUse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface StabSampleUsageApprDao {
    List<StabSampleUsageApprVO> find(StabSampleUsageApprVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.requestApproveUse)
    int approve(StabSampleUsageApprVO param);

    @Audit(target = QsSmpUse.class, label = AuditEvent.Sample.rejectSampleUsage)
    int reject(StabSampleUsageApprVO param);
}
