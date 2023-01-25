package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.RmRitm;
import lims.api.ms.vo.TestMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;
import spring.audit.type.CommandType;

import java.util.List;

@Mapper
@Repository
public interface TestMaterialManageDao {

    List<TestMaterialVO> findAll(TestMaterialVO param);

    @Audit(target = RmRitm.class, label = AuditEvent.Material.create)
    int create(TestMaterialVO param);

    @Audit(target = RmRitm.class, label = AuditEvent.Material.update)
    int update(TestMaterialVO param);

    @Audit(target = RmRitm.class, label = AuditEvent.Material.delete, commandType = CommandType.DELETE)
    int delete(TestMaterialVO param);

}