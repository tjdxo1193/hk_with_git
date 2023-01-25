package lims.api.pr.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.pr.vo.PrintReportVO;
import lims.api.ts.entity.QtPitmAnsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface PrintReportDao {
    List<PrintReportVO> getTestReportList(PrintReportVO request);

    List<PrintReportVO> getTestItmList(PrintReportVO request);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.ReportEvent.updateReportInfo)
    int updateRptInfo(PrintReportVO request);
}
