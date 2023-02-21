package lims.api.sd.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmMng;
import lims.api.sd.vo.StandardMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface StdItemOpenDisDao {

    List<StandardMaterialVO> findAll(StandardMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.StandardMaterial.open)
    int open(StandardMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.StandardMaterial.requestDisposal)
    int requestDisposal(StandardMaterialVO param);
}
