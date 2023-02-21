package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.QmPitm;
import lims.api.ms.entity.QmPitmAitmSpec;
import lims.api.ms.entity.QmPitmSpec;
import lims.api.ms.entity.QmPitmSpecAitm;
import lims.api.ms.vo.ItemApprSpecAitmVO;
import lims.api.ms.vo.ItemApprSpecVO;
import lims.api.ms.vo.ItemManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Repository
@Mapper
public interface ItemApprDao {
    List<ItemManageVO> find(ItemManageVO param);

    ItemManageVO findAitmSpecIdxBySapPrdha(ItemManageVO param);

    ItemManageVO findSpecByPitmCd(ItemManageVO param);

    ItemManageVO findNewAitmSpecIdx(ItemManageVO param);

    List<ItemApprSpecAitmVO> findSpecAitm(ItemApprSpecVO param);

    @Audit(target = QmPitm.class, label = AuditEvent.Item.approve)
    int approve(ItemManageVO param);

    @Audit(target = QmPitm.class, label = AuditEvent.Item.rejecte)
    int reject(ItemManageVO param);

    @Audit(target = QmPitm.class, label = AuditEvent.Item.updateUseVerYn)
    int updatePitm(ItemManageVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Item.updateSpecUseVerYn)
    int updateSpec(ItemManageVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Item.updateSpecPitmVer)
    int updateSpecPitmVer(ItemManageVO param);

    @Audit(target = QmPitmAitmSpec.class, label = AuditEvent.Item.createSpec)
    int createQmPitmAitmSpec(ItemManageVO param);

    @Audit(target = QmPitmSpecAitm.class, label = AuditEvent.Item.createSpec)
    int createQmPitmSpecAitm(ItemApprSpecAitmVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Item.createSpec)
    int createQmPitmSpec(ItemManageVO param);
}
