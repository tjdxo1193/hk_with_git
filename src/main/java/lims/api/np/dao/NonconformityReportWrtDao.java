package lims.api.np.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.QtPitmAnsInfo;
import lims.api.np.vo.NonconformityReportWrtVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface NonconformityReportWrtDao {
    List<NonconformityReportWrtVO> findAll(NonconformityReportWrtVO param);

    List<NonconformityReportWrtVO> findResultItem(NonconformityReportWrtVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.NonconformityReport.update)
    int save(NonconformityReportWrtVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.NonconformityReport.requestApprove)
    int requestAppr(NonconformityReportWrtVO param);
}
