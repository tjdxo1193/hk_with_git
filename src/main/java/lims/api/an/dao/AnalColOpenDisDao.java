package lims.api.an.dao;

import lims.api.an.entity.RtRitmEtrInfoAnc;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface AnalColOpenDisDao {

    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.AnalColMaterial.open)
    int open(AnalColMaterialVO param);

    @Audit(target = RtRitmEtrInfoAnc.class, label = AuditEvent.AnalColMaterial.openUpdate)
    int update(AnalColMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.AnalColMaterial.requestDisposal)
    int disposal(AnalColMaterialVO param);
}
