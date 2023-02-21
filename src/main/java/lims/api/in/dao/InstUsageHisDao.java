package lims.api.in.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.in.entity.EmEqmUse;
import lims.api.in.vo.InstUsageHisVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface InstUsageHisDao {
    List<InstUsageHisVO> find(InstUsageHisVO param);

    List<InstUsageHisVO> findInstrument(InstUsageHisVO param);

    @Audit(target = EmEqmUse.class, label = AuditEvent.Instrument.createUsageHistory)
    int create(InstUsageHisVO param);

    @Audit(target = EmEqmUse.class, label = AuditEvent.Instrument.updateUsageHistory)
    int update(InstUsageHisVO param);

    @Audit(target = EmEqmUse.class, label = AuditEvent.Instrument.deleteUsageHistory)
    int delete(InstUsageHisVO param);

    @Audit(target = EmEqmUse.class, label = AuditEvent.Instrument.savedFile)
    int updateFile(InstUsageHisVO param);
}
