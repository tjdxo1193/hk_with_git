package lims.api.st.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.st.vo.StabSampleDisApprVO;
import lims.api.tp.entity.QsSmpMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface StabSampleDisApprDao {
    List<StabSampleDisApprVO> find(StabSampleDisApprVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.approve)
    int approve(StabSampleDisApprVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.reject)
    int reject(StabSampleDisApprVO param);
}
