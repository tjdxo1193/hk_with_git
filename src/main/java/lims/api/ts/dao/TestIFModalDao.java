package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.MsSrmReoPrev;
import lims.api.ts.vo.TestIFModalVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface TestIFModalDao {
    List<TestIFModalVO> getSrmFinalOrderList(TestIFModalVO request);

    List<TestIFModalVO> getMesFinalOrderList(TestIFModalVO request);

    List<TestIFModalVO> getPrvRcrReportList(TestIFModalVO request);

    List<TestIFModalVO> getSrmReportList(TestIFModalVO request);

    List<TestIFModalVO> getPackingSpecList(TestIFModalVO request);

    List<TestIFModalVO> getInpPerformanceList(TestIFModalVO request);

    int getSeq(TestIFModalVO request);

    @Audit(target = MsSrmReoPrev.class, label = AuditEvent.PreventRecurrenceReport.saveReport)
    int savePrvRcrReport(TestIFModalVO param);

    @Audit(target = MsSrmReoPrev.class, label = AuditEvent.PreventRecurrenceReport.deleteReport)
    int deletePrvRcrReport(TestIFModalVO param);
}
