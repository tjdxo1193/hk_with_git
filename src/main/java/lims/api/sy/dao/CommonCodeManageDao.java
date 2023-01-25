package lims.api.sy.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sy.entity.SyCdDtl;
import lims.api.sy.entity.SyCdHir;
import lims.api.sy.vo.CommonCodeVO;
import lims.api.sy.vo.CommonDetailCodeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface CommonCodeManageDao {

    List<CommonCodeVO> findUpperCode(CommonCodeVO param);

    List<CommonDetailCodeVO> findDetailCode(CommonDetailCodeVO param);

    @Audit(target = SyCdHir.class, label = AuditEvent.CommonCode.create)
    int upperCodeInsert(CommonCodeVO param);

    @Audit(target = SyCdHir.class, label = AuditEvent.CommonCode.update)
    int upperCodeUpdate(CommonCodeVO param);

    @Audit(target = SyCdDtl.class, label = AuditEvent.CommonCode.detailCreate)
    int upperDetailCodeInsert(CommonDetailCodeVO param);

    @Audit(target = SyCdDtl.class, label = AuditEvent.CommonCode.detailUpdate)
    int upperDetailCodeUpdate(CommonDetailCodeVO param);
}
