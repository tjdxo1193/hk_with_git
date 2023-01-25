package lims.api.sm.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sm.entity.SdPsSpcm;
import lims.api.sm.vo.ProcessSpecimenManageVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface ProcessSpecimenManageDao {
    List<ProcessSpecimenManageVO> getProcessSpecimenList(ProcessSpecimenManageVO request);

    @Audit(target = SdPsSpcm.class, label = AuditEvent.ProcessSpecimen.create)
    int insertProcessSpecimen(ProcessSpecimenManageVO request);
    @Audit(target = SdPsSpcm.class, label = AuditEvent.ProcessSpecimen.update)
    int updateProcessSpecimen(ProcessSpecimenManageVO request);
}
