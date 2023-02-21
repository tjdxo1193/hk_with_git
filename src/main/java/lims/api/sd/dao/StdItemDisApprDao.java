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
public interface StdItemDisApprDao {

    List<StandardMaterialVO> findAll(StandardMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.Approve.approve)
    int approve(StandardMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.Approve.reject)
    int reject(StandardMaterialVO param);
}
