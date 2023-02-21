package lims.api.lb.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.lb.vo.LabEventApprVO;
import lims.api.ts.entity.QeLabEvt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface LabEventApprDao {
    List<LabEventApprVO> findAll(LabEventApprVO dto);
    @Audit(target = QeLabEvt.class, label = AuditEvent.labEvent.approve)
    Integer approve(LabEventApprVO dto);
    @Audit(target = QeLabEvt.class, label = AuditEvent.labEvent.reject)
    Integer reject(LabEventApprVO dto);
}
