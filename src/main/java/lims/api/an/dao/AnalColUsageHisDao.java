package lims.api.an.dao;

import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmUse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface AnalColUsageHisDao {

    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);

    List<AnalColMaterialVO> findOpenItem(AnalColMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.AnalColMaterial.useCreate)
    int create(AnalColMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.AnalColMaterial.useUpdate)
    int update(AnalColMaterialVO param);

    @Audit(target = RtRitmUse.class, label = AuditEvent.AnalColMaterial.useDelete, commandType = CommandType.DELETE)
    int delete(AnalColMaterialVO param);
}
