package lims.api.re.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.re.vo.ReagentMaterialVO;
import lims.api.sd.entity.RtRitmUse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface ReagentUsageHisDao {

    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.ReagentMaterial.useCreate)
    int create(ReagentMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.ReagentMaterial.useUpdate)
    int update(ReagentMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.ReagentMaterial.useDelete, commandType = CommandType.DELETE)
    int delete(ReagentMaterialVO param);
}
