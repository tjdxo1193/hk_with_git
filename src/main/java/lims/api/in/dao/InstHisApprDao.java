package lims.api.in.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.in.entity.EmEqmHis;
import lims.api.in.vo.InstHisApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface InstHisApprDao {
    List<InstHisApprVO> findAll(InstHisApprVO param);

    List<InstHisApprVO> findByHisSeq(Integer hisSeq);

    @Audit(target = EmEqmHis.class, label = AuditEvent.Equipment.approve)
    int approve(InstHisApprVO param);

    @Audit(target = EmEqmHis.class, label = AuditEvent.Equipment.reject)
    int reject(InstHisApprVO param);
}
