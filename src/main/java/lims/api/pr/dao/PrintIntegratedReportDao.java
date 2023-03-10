package lims.api.pr.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.pr.entity.QtPitmAnsArpt;
import lims.api.pr.vo.PrintIntegratedReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Repository
@Mapper
public interface PrintIntegratedReportDao {
    List<PrintIntegratedReportVO> find(PrintIntegratedReportVO param);

    List<PrintIntegratedReportVO> findTestItem(PrintIntegratedReportVO param);

    List<PrintIntegratedReportVO> findReportHistory(PrintIntegratedReportVO param);

    @Audit(target = QtPitmAnsArpt.class, label = AuditEvent.IntegratedReportPrint.createReport)
    int create(PrintIntegratedReportVO param);

    @Audit(target = QtPitmAnsArpt.class, label = AuditEvent.IntegratedReportPrint.updateReport)
    int update(PrintIntegratedReportVO param);

    PrintIntegratedReportVO findReportDetail(PrintIntegratedReportVO param);
}
