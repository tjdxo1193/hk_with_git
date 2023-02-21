package lims.api.in.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.in.entity.EmEqmAcsr;
import lims.api.in.entity.EmEqmInfo;
import lims.api.in.vo.InstManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface InstManageDao {
    List<InstManageVO> find(InstManageVO param);

    List<InstManageVO> findAccessory(InstManageVO param);

    @Audit(target = EmEqmInfo.class, label = AuditEvent.Instrument.create)
    int create(InstManageVO param);

    @Audit(target = EmEqmAcsr.class, label = AuditEvent.Instrument.createAccessory)
    int createAccessory(InstManageVO param);

    @Audit(target = EmEqmInfo.class, label = AuditEvent.Instrument.update)
    int update(InstManageVO param);

    @Audit(target = EmEqmInfo.class, label = AuditEvent.Instrument.delete)
    int delete(InstManageVO param);

    @Audit(target = EmEqmAcsr.class, label = AuditEvent.Instrument.updateAccessory)
    int updateAccessory(InstManageVO param);

    @Audit(target = EmEqmAcsr.class, label = AuditEvent.Instrument.deleteAccessory)
    int deleteAccessory(InstManageVO param);

    @Audit(target = EmEqmAcsr.class, label = AuditEvent.Instrument.deleteAccessory)
    int deleteAllAccessory(InstManageVO param);

    @Audit(target = EmEqmInfo.class, label = AuditEvent.Instrument.savedFile)
    int updateFile(InstManageVO param);
}
