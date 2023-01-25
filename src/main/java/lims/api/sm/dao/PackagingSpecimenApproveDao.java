package lims.api.sm.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sm.entity.SdPmSpcm;
import lims.api.sm.entity.SdPmSpcmAcsr;
import lims.api.sm.entity.SdPmSpcmNo;
import lims.api.sm.vo.PackagingSpecimenApproveVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface PackagingSpecimenApproveDao {
    List<PackagingSpecimenApproveVO> getPackagingSpecimenList(PackagingSpecimenApproveVO request);
    List<PackagingSpecimenApproveVO> getPackagingSpecimenAcsr(PackagingSpecimenApproveVO request);

    @Audit(target = SdPmSpcmNo.class, label = AuditEvent.PackagingSpecimen.delete)
    int approve(PackagingSpecimenApproveVO request);
    @Audit(target = SdPmSpcm.class, label = AuditEvent.PackagingSpecimen.delete)
    int updatePackagingSpecimenProcess(PackagingSpecimenApproveVO request);

    @Audit(target = SdPmSpcm.class, label = AuditEvent.PackagingSpecimen.reject)
    int reject(PackagingSpecimenApproveVO request);

    @Audit(target = SdPmSpcmAcsr.class, label = AuditEvent.PackagingSpecimen.deleteAcsr)
    int deletePackagingSpecimenAcsr(PackagingSpecimenApproveVO request);
}
