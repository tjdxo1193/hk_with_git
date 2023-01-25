package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.SyPlnt;
import lims.api.sy.vo.PlantManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface PlantManageDao {

    List<PlantManageVO> findAll(PlantManageVO param);

    String nextCode();

    @Audit(target = SyPlnt.class, label = AuditEvent.Plant.create)
    int create(PlantManageVO param);

    @Audit(target = SyPlnt.class, label = AuditEvent.Plant.update)
    int update(PlantManageVO param);

}