package lims.api.np.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.np.vo.NonconformityReportApprVO;
import lims.api.ts.entity.QtPitmAnsInfo;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface NonconformityReportApprDao {
    List<NonconformityReportApprVO> findAll(NonconformityReportApprVO param);

    List<NonconformityReportApprVO> findResultItem(NonconformityReportApprVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.NonconformityReport.approve)
    int approve(NonconformityReportApprVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.NonconformityReport.reject)
    int reject(NonconformityReportApprVO param);
}
