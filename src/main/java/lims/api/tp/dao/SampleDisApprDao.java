package lims.api.tp.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.tp.entity.QsSmpMng;
import lims.api.tp.vo.SampleDisApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface SampleDisApprDao {
    List<SampleDisApprVO> find(SampleDisApprVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.approve)
    int approve(SampleDisApprVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.reject)
    int reject(SampleDisApprVO param);
}
