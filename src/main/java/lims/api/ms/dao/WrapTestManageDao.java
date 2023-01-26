package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.QmPitmSpecAitm;
import lims.api.ms.entity.QmPkga;
import lims.api.ms.vo.WrapTestManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface WrapTestManageDao {
	List<WrapTestManageVO> getList(WrapTestManageVO param);
	WrapTestManageVO getOne(WrapTestManageVO param);
	List<WrapTestManageVO> getSapList(WrapTestManageVO param);
	List<WrapTestManageVO> getVersion(WrapTestManageVO param);
	List<WrapTestManageVO> getTestItem(WrapTestManageVO param);
	List<WrapTestManageVO> getSpec(WrapTestManageVO param);

	List<WrapTestManageVO> getBeforeVersionList(WrapTestManageVO param);

	@Audit(target = QmPkga.class, label = AuditEvent.WrapTest.createWrapTest)
	Integer insertVersion(WrapTestManageVO param);
	@Audit(target = QmPitmSpecAitm.class, label = AuditEvent.WrapTest.updateWrapTestSpecAItem)
	Integer putTestItem(WrapTestManageVO param);
	@Audit(target = QmPitmSpecAitm.class, label = AuditEvent.WrapTest.deleteWrapTestSpecAItem)
	Integer deleteTestItem(WrapTestManageVO param);
	@Audit(target = QmPkga.class, label = AuditEvent.WrapTest.updateWrapTest)
	Integer putWrapTest(WrapTestManageVO param);
	@Audit(target = QmPkga.class, label = AuditEvent.WrapTest.updateWrapTest)
	Integer updateWrapTest(WrapTestManageVO param);
	@Audit(target = QmPkga.class, label = AuditEvent.WrapTest.updateWrapTest)
	Integer updateWrapTestUseYn(WrapTestManageVO param);
	@Audit(target = QmPkga.class, label = AuditEvent.WrapTest.updateWrapTest)
	int updateApprovalSideEffect(WrapTestManageVO param);
	@Audit(target = QmPkga.class, label = AuditEvent.WrapTest.requestApproveWrapTest)
	Integer approval(WrapTestManageVO param);
	Integer getSapPrdhaDuplicateCheck(WrapTestManageVO param);
}