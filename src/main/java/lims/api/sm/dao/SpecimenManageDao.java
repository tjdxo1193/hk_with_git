package lims.api.sm.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sm.entity.SdSpcm;
import lims.api.sm.entity.SdSpcmHis;
import lims.api.sm.vo.SpecimenManageVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface SpecimenManageDao {
    List<SpecimenManageVO> getSpecimenList(SpecimenManageVO request);
    List<SpecimenManageVO> getSpecimenHis(SpecimenManageVO request);

    @Audit(target = SdSpcm.class, label = AuditEvent.Specimen.create)
    int insertSpecimen(SpecimenManageVO request);
    @Audit(target = SdSpcm.class, label = AuditEvent.Specimen.update)
    int updateSpecimen(SpecimenManageVO request);
    @Audit(target = SdSpcmHis.class, label = AuditEvent.Specimen.revise)
    int insertSpecimenHis(SpecimenManageVO request);
}
