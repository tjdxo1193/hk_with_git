package lims.api.sd.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmUse;
import lims.api.sd.vo.StandardMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface StdItemUsageHisDao {

    List<StandardMaterialVO> findAll(StandardMaterialVO param);

    List<StandardMaterialVO> findOpenItem(StandardMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.StandardMaterial.useCreate)
    int create(StandardMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.StandardMaterial.useUpdate)
    int update(StandardMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.StandardMaterial.useDelete, commandType = CommandType.DELETE)
    int delete(StandardMaterialVO param);
}
