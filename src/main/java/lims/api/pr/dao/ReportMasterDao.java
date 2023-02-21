package lims.api.pr.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.pr.entity.SyRptMst;
import lims.api.pr.vo.ReportMasterVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface ReportMasterDao {
    List<ReportMasterVO> findAll(ReportMasterVO reportMasterVO);

    @Audit(target = SyRptMst.class, label = AuditEvent.RptMst.create)
    int create(ReportMasterVO reportMasterVO);

    @Audit(target = SyRptMst.class, label = AuditEvent.RptMst.update)
    int update(ReportMasterVO reportMasterVO);
    
    @Audit(target = SyRptMst.class, label = AuditEvent.RptMst.delete)
    int delete(ReportMasterVO reportMasterVO);
}