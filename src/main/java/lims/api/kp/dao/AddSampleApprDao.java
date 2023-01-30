package lims.api.kp.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.kp.entity.QsAddSmpMng;
import lims.api.kp.vo.AddSampleApprVO;
import lims.api.tp.entity.QsSmpMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface AddSampleApprDao {
    List<AddSampleApprVO> find(AddSampleApprVO param);

    @Audit(target = QsAddSmpMng.class, label = AuditEvent.Sample.approveAddSample)
    int approve(AddSampleApprVO param);

    @Audit(target = QsAddSmpMng.class, label = AuditEvent.Sample.rejectAddSample)
    int reject(AddSampleApprVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.createSample, commandType = CommandType.INSERT)
    int createSample(AddSampleApprVO param);
}
