package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.QmPitmAitmSpec;
import lims.api.ms.entity.QmPitmSpec;
import lims.api.ms.entity.QmPitmSpecAitm;
import lims.api.ms.vo.SpecManageAitmVO;
import lims.api.ms.vo.SpecManageDptVO;
import lims.api.ms.vo.SpecManagePitmVO;
import lims.api.ms.vo.SpecManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface SpecManageDao {
    List<SpecManagePitmVO> getPItemList(SpecManageVO param);

    List<SpecManageVO> getVersionList(SpecManageVO param);

    List<SpecManageAitmVO> getAItemList(SpecManageVO param);

    List<SpecManageVO> getItemMethodList(SpecManageVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Spec.updateRequestReview)
    int updateRequestReview(SpecManageVO param);

    @Audit(target = QmPitmAitmSpec.class, label = AuditEvent.Spec.createVersion)
    int createAitmSpec(SpecManageVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Spec.updateUseVerN)
    int updateUseVerN(SpecManageVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Spec.createPItemSpec)
    int createPItemSpec(SpecManageVO param);

    @Audit(target = QmPitmSpecAitm.class, label = AuditEvent.Spec.deletePItemSpecAItem)
    int deletePItemSpecAItem(SpecManageAitmVO param);

    @Audit(target = QmPitmSpecAitm.class, label = AuditEvent.Spec.createPItemSpecAItem)
    int createPItemSpecAItem(SpecManageAitmVO param);

    @Audit(target = QmPitmSpecAitm.class, label = AuditEvent.Spec.updatePItemSpecAItem)
    int updatePItemSpecAItem(SpecManageAitmVO param);

    @Audit(target = QmPitmSpecAitm.class, label = AuditEvent.Spec.revisionSpec)
    int updatePItemSpec(SpecManageVO param);

    List<SpecManageDptVO> getDepartmentList(SpecManageVO param);

    List<SpecManagePitmVO> getPItemSpecListToModal(SpecManageVO param);

    List<SpecManageAitmVO> getAItemListToModal(SpecManageVO param);

    List<SpecManageAitmVO> getSemiAItemList(SpecManageVO param);

    List<SpecManagePitmVO> getPackagingItemListToModal(SpecManageVO param);

    List<SpecManageVO> getSemiPItemListToModal(SpecManageVO param);

    List<SpecManageAitmVO> getSemiAItemListToModal(SpecManageVO param);
}
