package lims.api.re.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.re.vo.ReagentMaterialVO;
import lims.api.sd.entity.RtRitmMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface ReagentOpenCancelDao {

    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.ReagentMaterial.openCancel)
    int cancel(ReagentMaterialVO param);
}
