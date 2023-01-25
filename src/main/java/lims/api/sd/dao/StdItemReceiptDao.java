package lims.api.sd.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmEtr;
import lims.api.sd.entity.RtRitmEtrInfoStd;
import lims.api.sd.vo.StandardMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface StdItemReceiptDao {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.StandardMaterial.create)
    int create(StandardMaterialVO param);

    @Audit(target = RtRitmEtrInfoStd.class, label = AuditEvent.StandardMaterial.create)
    int createStandard(StandardMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.StandardMaterial.update)
    int update(StandardMaterialVO param);

    @Audit(target = RtRitmEtrInfoStd.class, label = AuditEvent.StandardMaterial.update)
    int updateStandard(StandardMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.StandardMaterial.delete, commandType = CommandType.DELETE)
    int delete(StandardMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.StandardMaterial.approve)
    int requestApprove(StandardMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.StandardMaterial.savedFile)
    int savedFile(StandardMaterialVO param);
}
