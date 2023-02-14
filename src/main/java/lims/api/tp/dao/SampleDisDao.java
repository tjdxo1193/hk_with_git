package lims.api.tp.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.tp.entity.QsSmpMng;
import lims.api.tp.vo.SampleDisVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface SampleDisDao {
    List<SampleDisVO> find(SampleDisVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.requestApproveDispose)
    int requestDis(SampleDisVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.requestApproveCancelDispose)
    int requestDisCancel(SampleDisVO param);
}
