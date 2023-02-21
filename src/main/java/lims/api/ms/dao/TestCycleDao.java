package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.RmRitm;
import lims.api.ms.entity.SyAnsCyl;
import lims.api.ms.vo.TestCycleVO;
import lims.api.ms.vo.TestMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface TestCycleDao {

    List<TestCycleVO> getList(TestCycleVO param);

    @Audit(target = SyAnsCyl.class, label = AuditEvent.TestCycle.create)
    int create(TestCycleVO param);

    @Audit(target = SyAnsCyl.class, label = AuditEvent.TestCycle.update)
    int update(TestCycleVO param);

    @Audit(target = SyAnsCyl.class, label = AuditEvent.TestCycle.delete, commandType = CommandType.DELETE)
    int delete(TestCycleVO param);
}