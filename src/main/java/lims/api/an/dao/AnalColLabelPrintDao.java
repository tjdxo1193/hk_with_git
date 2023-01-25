package lims.api.an.dao;

import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmEtr;
import lims.api.sd.entity.RtRitmMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface AnalColLabelPrintDao {

    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.AnalColMaterial.stock)
    int create(AnalColMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.AnalColMaterial.printLabel)
    int updateEtrState(AnalColMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.AnalColMaterial.printLabel)
    int updateMngState(AnalColMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.AnalColMaterial.reprintLabel)
    int reprint(AnalColMaterialVO param);
}
