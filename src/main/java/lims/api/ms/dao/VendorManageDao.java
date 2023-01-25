package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.RmRitmVdr;
import lims.api.ms.vo.VendorVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface VendorManageDao {
    List<VendorVO> findAll(VendorVO param);

    @Audit(target = RmRitmVdr.class, label = AuditEvent.Vendor.create)
    int create(VendorVO param);

    @Audit(target = RmRitmVdr.class, label = AuditEvent.Vendor.update)
    int update(VendorVO param);

    @Audit(target = RmRitmVdr.class, label = AuditEvent.Vendor.delete)
    int delete(VendorVO param);
}