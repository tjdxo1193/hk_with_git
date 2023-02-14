package lims.api.np.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.np.vo.PreventRecurrenceApprVO;
import lims.api.ts.entity.QtPitmAnsInfo;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface PreventRecurrenceApprDao {
    List<PreventRecurrenceApprVO> findAll(PreventRecurrenceApprVO param);

    List<PreventRecurrenceApprVO> findResultItem(PreventRecurrenceApprVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.PreventRecurrenceReport.approve)
    int approve(PreventRecurrenceApprVO param);
}
