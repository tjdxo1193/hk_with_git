package lims.api.st.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.st.entity.StSbtPln;
import lims.api.st.vo.StabPlanDetailApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface StabPlanDetailApprDao {
    List<StabPlanDetailApprVO> findAll(StabPlanDetailApprVO param);
    @Audit(target = StSbtPln.class, label = AuditEvent.StabPln.approve)
    int approve(StabPlanDetailApprVO param);
    @Audit(target = StSbtPln.class, label = AuditEvent.StabPln.reject)
    int reject(StabPlanDetailApprVO param);
}
