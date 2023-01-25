package lims.api.re.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.re.vo.ReagentMaterialVO;
import lims.api.sd.entity.RtRitmEtr;
import lims.api.sd.entity.RtRitmMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface ReagentLabelPrintDao {

    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.ReagentMaterial.stock)
    int create(ReagentMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.ReagentMaterial.printLabel)
    int updateEtrState(ReagentMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.ReagentMaterial.printLabel)
    int updateMngState(ReagentMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.ReagentMaterial.reprintLabel)
    int reprint(ReagentMaterialVO param);
}
