package lims.api.an.dao;

import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface AnalColDisApprDao {

    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.Approve.approve)
    int approve(AnalColMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.Approve.reject)
    int reject(AnalColMaterialVO param);
}
