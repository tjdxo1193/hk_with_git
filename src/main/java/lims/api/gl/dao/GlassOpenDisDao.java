package lims.api.gl.dao;
import lims.api.common.domain.AuditEvent;
import lims.api.gl.vo.GlassMaterialVO;
import lims.api.sd.entity.RtRitmEtr;
import lims.api.sd.entity.RtRitmMng;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface GlassOpenDisDao {

    List<GlassMaterialVO> findAll(GlassMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.GlassMaterial.open)
    int create(GlassMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.GlassMaterial.open)
    int open(GlassMaterialVO param);

    @Audit(target = RtRitmMng.class, label = AuditEvent.GlassMaterial.disposal)
    int disposal(GlassMaterialVO param);
}