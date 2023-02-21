package lims.api.sm.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sm.entity.SdPmSpcm;
import lims.api.sm.entity.SdPmSpcmAcsr;
import lims.api.sm.entity.SdPmSpcmNo;
import lims.api.sm.vo.PackagingSpecimenManageVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface PackagingSpecimenManageDao {
    List<PackagingSpecimenManageVO> getPackagingSpecimenList(PackagingSpecimenManageVO request);
    List<PackagingSpecimenManageVO> getPackagingSpecimenAcsr(PackagingSpecimenManageVO request);

    @Audit(target = SdPmSpcmNo.class, label = AuditEvent.PackagingSpecimen.create)
    int insertPackagingSpecimenNo(PackagingSpecimenManageVO request);
    @Audit(target = SdPmSpcmNo.class, label = AuditEvent.PackagingSpecimen.create)
    int updatePackagingSpecimenNo(PackagingSpecimenManageVO request);

    @Audit(target = SdPmSpcm.class, label = AuditEvent.PackagingSpecimen.create)
    int insertPackagingSpecimen(PackagingSpecimenManageVO request);
    @Audit(target = SdPmSpcm.class, label = AuditEvent.PackagingSpecimen.update)
    int updatePackagingSpecimen(PackagingSpecimenManageVO request);

    @Audit(target = SdPmSpcm.class, label = AuditEvent.PackagingSpecimen.deleteRequest)
    int deleteRequest(PackagingSpecimenManageVO request);

    @Audit(target = SdPmSpcmAcsr.class, label = AuditEvent.PackagingSpecimen.addAcsr)
    int insertPackagingSpecimenAcsr(PackagingSpecimenManageVO request);
    @Audit(target = SdPmSpcmAcsr.class, label = AuditEvent.PackagingSpecimen.updateAcsr)
    int updatePackagingSpecimenAcsr(PackagingSpecimenManageVO request);
    @Audit(target = SdPmSpcmAcsr.class, label = AuditEvent.PackagingSpecimen.deleteAcsr)
    int deletePackagingSpecimenAcsr(PackagingSpecimenManageVO request);

    int getPackagingSpecimenNoCount(PackagingSpecimenManageVO request);

}
