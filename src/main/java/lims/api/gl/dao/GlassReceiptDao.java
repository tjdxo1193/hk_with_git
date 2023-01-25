package lims.api.gl.dao;
import lims.api.common.domain.AuditEvent;
import lims.api.gl.entity.RtRitmEtrInfoGls;
import lims.api.gl.vo.GlassMaterialVO;
import lims.api.re.entity.RtRitmEtrInfoReg;
import lims.api.sd.entity.RtRitmEtr;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface GlassReceiptDao {
    List<GlassMaterialVO> findAll(GlassMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.GlassMaterial.create)
    int create(GlassMaterialVO param);

    @Audit(target = RtRitmEtrInfoGls.class, label = AuditEvent.GlassMaterial.create)
    int createGlass(GlassMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.GlassMaterial.update)
    int update(GlassMaterialVO param);

    @Audit(target = RtRitmEtrInfoGls.class, label = AuditEvent.GlassMaterial.update)
    int updateGlass(GlassMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.GlassMaterial.delete, commandType = CommandType.DELETE)
    int delete(GlassMaterialVO param);

    @Audit(target = RtRitmEtr.class, label = AuditEvent.GlassMaterial.savedFile)
    int savedFile(GlassMaterialVO param);
}