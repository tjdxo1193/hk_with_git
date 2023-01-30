package lims.api.kp.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.kp.entity.QsAddSmpMng;
import lims.api.kp.vo.AddSampleRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface AddSampleRequestDao {
    List<AddSampleRequestVO> find(AddSampleRequestVO param);

    @Audit(target = QsAddSmpMng.class, label = AuditEvent.Sample.requestApproveAddSample)
    int requestApprove(AddSampleRequestVO param);

    @Audit(target = QsAddSmpMng.class, label = AuditEvent.Sample.createAddSample, commandType = CommandType.INSERT)
    int create(AddSampleRequestVO param);

    @Audit(target = QsAddSmpMng.class, label = AuditEvent.Sample.updateAddSample, commandType = CommandType.UPDATE)
    int update(AddSampleRequestVO param);

    @Audit(target = QsAddSmpMng.class, label = AuditEvent.Sample.deleteAddSample, commandType = CommandType.DELETE)
    int delete(AddSampleRequestVO param);

    String findLabelCd(AddSampleRequestVO param);
}
