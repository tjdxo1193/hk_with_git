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
public interface StaffJobDelegationDao {
    List<StaffJobDelegationVO> find(StaffJobDelegationVO param);

    @Audit(target = SyUserDlg.class, label = AuditEvent.UserDelegate.tempSave)
    int create(StaffJobDelegationVO param);

    @Audit(target = SyUserDlg.class, label = AuditEvent.UserDelegate.update)
    int update(StaffJobDelegationVO param);

    @Audit(target = SyUserDlg.class, label = AuditEvent.UserDelegate.requestApprove)
    int updateApprRequest(StaffJobDelegationVO param);

    @Audit(target = SyUserDlg.class, label = AuditEvent.UserDelegate.stay)
    int updateGbkRegist(StaffJobDelegationVO param);
}
