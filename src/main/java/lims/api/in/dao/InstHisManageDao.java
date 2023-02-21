package lims.api.in.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import lims.api.common.domain.AuditEvent;
import lims.api.in.entity.EmEqmHis;
import lims.api.in.vo.InstHisManageVO;
import spring.audit.annotation.Audit;

@Mapper
@Repository
public interface InstHisManageDao {

	List<InstHisManageVO> findAll(InstHisManageVO param);

	@Audit(target = EmEqmHis.class, label = AuditEvent.Equipment.historySave)
	int save(InstHisManageVO param);

	@Audit(target = EmEqmHis.class, label = AuditEvent.Equipment.historyUpdate)
	int update(InstHisManageVO param);

	@Audit(target = EmEqmHis.class, label = AuditEvent.Equipment.historyDelete)
	int delete(InstHisManageVO param);

	@Audit(target = EmEqmHis.class, label = AuditEvent.Equipment.requestApprove)
	int requestApprove(InstHisManageVO param);
}
