package lims.api.tp.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.tp.entity.QsSmpMng;
import lims.api.tp.vo.SampleManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface SampleManageDao {
    List<SampleManageVO> find(SampleManageVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.createSample, commandType = CommandType.INSERT)
    int create(SampleManageVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.updateSample, commandType = CommandType.UPDATE)
    int update(SampleManageVO param);

    @Audit(target = QsSmpMng.class, label = AuditEvent.Sample.deleteSample, commandType = CommandType.DELETE)
    int delete(SampleManageVO param);

    List<SampleManageVO> findTest(SampleManageVO param);
}
