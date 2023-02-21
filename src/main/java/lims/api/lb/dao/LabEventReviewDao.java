package lims.api.lb.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.in.entity.EmEqmHis;
import lims.api.lb.vo.LabEventReviewVO;
import lims.api.ms.entity.QmPkga;
import lims.api.ts.entity.QeLabEvt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface LabEventReviewDao {
    List<LabEventReviewVO> findAll(LabEventReviewVO dto);
    @Audit(target = QeLabEvt.class, label = AuditEvent.labEvent.approveRequest)
    Integer approveRequest(LabEventReviewVO dto);
    @Audit(target = QeLabEvt.class, label = AuditEvent.labEvent.reject)
    Integer reject(LabEventReviewVO dto);
}
