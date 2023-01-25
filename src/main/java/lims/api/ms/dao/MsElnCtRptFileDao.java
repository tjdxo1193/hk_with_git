package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.MsElnCtRptFile;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.MsElnCtRptFileVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
public interface MsElnCtRptFileDao {

    List<MsElnCtRptFileVO> getMsElnCtRptFileList(MsElnCtRptFileVO param);

    String nextIdx();

    String nextSeq(MsElnCtRptFileVO param);

    @Audit(target = MsElnCtRptFile.class, label = AuditEvent.File.create)
    int save(MsElnCtRptFileVO param);

    @Audit(target = MsElnCtRptFile.class, label = AuditEvent.File.delete, commandType = CommandType.DELETE)
    int delete(MsElnCtRptFileVO param);

}
