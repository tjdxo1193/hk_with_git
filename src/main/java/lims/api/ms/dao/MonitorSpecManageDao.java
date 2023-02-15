package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.common.vo.ComboVO;
import lims.api.ms.entity.MmMitmAitmSpec;
import lims.api.ms.entity.MmMitmSpec;
import lims.api.ms.entity.MmMitmSpecAitm;
import lims.api.ms.vo.MonitorSpecManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface MonitorSpecManageDao {
    List<MonitorSpecManageVO> getMItemSpecList(MonitorSpecManageVO param);

    List<MonitorSpecManageVO> getVersionList(MonitorSpecManageVO param);

    List<MonitorSpecManageVO> getMItemSpecAItemList(MonitorSpecManageVO param);

    @Audit(target = MmMitmSpec.class, label = AuditEvent.MonitorSpec.createMItemSpec)
    int createMItemSpec(MonitorSpecManageVO param);

    @Audit(target = MmMitmAitmSpec.class, label = AuditEvent.MonitorSpec.createVersion)
    int createVersion(MonitorSpecManageVO param);

    @Audit(target = MmMitmSpecAitm.class, label = AuditEvent.MonitorSpec.createMItemSpecAItem)
    int createMItemSpecAItem(MonitorSpecManageVO param);

    @Audit(target = MmMitmSpecAitm.class, label = AuditEvent.MonitorSpec.updateMItemSpecAItem)
    int updateMItemSpecAItem(MonitorSpecManageVO param);

    @Audit(target = MmMitmSpecAitm.class, label = AuditEvent.MonitorSpec.deleteMItemSpecAItem)
    int deleteMItemSpecAItem(MonitorSpecManageVO param);

    @Audit(target = MmMitmSpec.class, label = AuditEvent.MonitorSpec.updateRequestReview)
    int updateRequestReview(MonitorSpecManageVO param);

    @Audit(target = MmMitmSpec.class, label = AuditEvent.MonitorSpec.deleteMItemSpec)
    int deleteMItemSpec(MonitorSpecManageVO param);

    List<MonitorSpecManageVO> getItemMethodList(MonitorSpecManageVO param);

    MonitorSpecManageVO getMitmSpecIdxAndAitmSpecIdx(String plntCd);
    @Audit(target = MmMitmSpecAitm.class, label = AuditEvent.MonitorSpec.updateUseVerN)
    int updateUseVerN(MonitorSpecManageVO param);

    List<ComboVO> getDepartmentList(MonitorSpecManageVO param);
}
