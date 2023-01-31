package lims.api.np.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.np.vo.PreventRecurrenceReviewVO;
import lims.api.ts.entity.QtPitmAnsInfo;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface PreventRecurrenceReviewDao {
    List<PreventRecurrenceReviewVO> findAll(PreventRecurrenceReviewVO param);

    List<PreventRecurrenceReviewVO> findResultItem(PreventRecurrenceReviewVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.PreventRecurrenceReport.update)
    int save(PreventRecurrenceReviewVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.PreventRecurrenceReport.requestApprove)
    int request(PreventRecurrenceReviewVO param);
}
