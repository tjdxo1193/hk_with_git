package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.SyDpt;
import lims.api.sy.vo.DepartmentManageVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface DepartmentManageDao {
    List<DepartmentManageVO> findAll(DepartmentManageVO vo);

    String getNextDeptCode(DepartmentManageVO vo);

    @Audit(target = SyDpt.class, label = AuditEvent.Department.create)
    int insert(DepartmentManageVO vo);

    @Audit(target = SyDpt.class, label = AuditEvent.Department.update)
    int update(DepartmentManageVO vo);

}