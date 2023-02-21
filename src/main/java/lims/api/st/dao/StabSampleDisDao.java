package lims.api.st.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.st.vo.StabSampleDisVO;
import lims.api.tp.entity.QsSmpMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface StabSampleDisDao {
    List<StabSampleDisVO> find(StabSampleDisVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.requestApproveDispose)
    int requestDispose(StabSampleDisVO params);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.requestApproveCancelDispose)
    int requestCancelDispose(StabSampleDisVO params);
}
