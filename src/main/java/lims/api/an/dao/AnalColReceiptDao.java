package lims.api.an.dao;

import lims.api.an.entity.RtRitmEtrInfoAnc;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmEtr;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface AnalColReceiptDao {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.AnalColMaterial.create)
    int create(AnalColMaterialVO param);

    @Audit(target = RtRitmEtrInfoAnc.class, label = AuditEvent.AnalColMaterial.create)
    int createAnalCol(AnalColMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.AnalColMaterial.update)
    int update(AnalColMaterialVO param);

    @Audit(target = RtRitmEtrInfoAnc.class, label = AuditEvent.AnalColMaterial.update)
    int updateAnalCol(AnalColMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.AnalColMaterial.delete, commandType = CommandType.DELETE)
    int delete(AnalColMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.AnalColMaterial.savedFile)
    int savedFile(AnalColMaterialVO param);
}
