package lims.api.re.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.re.entity.RtRitmEtrInfoReg;
import lims.api.re.vo.ReagentMaterialVO;
import lims.api.sd.entity.RtRitmEtr;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface ReagentReceiptDao {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.ReagentMaterial.create)
    int create(ReagentMaterialVO param);

    @Audit(target = RtRitmEtrInfoReg.class, label = AuditEvent.ReagentMaterial.create)
    int createReagent(ReagentMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.ReagentMaterial.update)
    int update(ReagentMaterialVO param);

    @Audit(target = RtRitmEtrInfoReg.class, label = AuditEvent.ReagentMaterial.update)
    int updateReagent(ReagentMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.ReagentMaterial.delete, commandType = CommandType.DELETE)
    int delete(ReagentMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.ReagentMaterial.savedFile)
    int savedFile(ReagentMaterialVO param);
}
