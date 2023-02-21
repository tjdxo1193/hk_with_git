package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.SyUserDlg;
import lims.api.ms.vo.StaffJobDelegationVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface StaffJobDelegationApprovalDao {
    List<StaffJobDelegationVO> find(StaffJobDelegationVO param);

    @Audit(target = SyUserDlg.class, label = AuditEvent.UserDelegate.complete)
    int updateApprove(StaffJobDelegationVO param);

    @Audit(target = SyUserDlg.class, label = AuditEvent.UserDelegate.delegationRegected)
    int updateReject(StaffJobDelegationVO param);
}
