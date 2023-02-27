package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.*;
import lims.api.ms.enums.ItemManage;
import lims.api.ms.vo.ItemApprSpecVO;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.MsElnCtRptFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Repository
@Mapper
public interface ItemManageDao {
    List<ItemManageVO> findPItem(ItemManageVO param);

    List<ItemManageVO> findVersion(ItemManageVO param);

    List<ItemManageVO> findSpecProcByPitmCd(ItemManageVO param);

    ItemManageVO findNewAitmSpecIdx(ItemApprSpecVO param);

    ItemApprSpecVO findOldSpecInfo(ItemManageVO param);

    @Audit(target = QmPitm.class, label = AuditEvent.Item.update)
    int updateQmPitm(ItemManageVO param);

    @Audit(target = QmPitmInfo.class, label = AuditEvent.Item.update)
    int updateQmPitmInfo(ItemManageVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Item.updateSpecPitmVer)
    int updateSpecNewPItemVer(ItemApprSpecVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Item.updateSpecUseVerYn)
    int updateOldSpecUseVerN(ItemApprSpecVO param);

    @Audit(target = QmPitm.class, label = AuditEvent.Item.create)
    int createQmPitm(ItemManageVO param);

    @Audit(target = QmPitmInfo.class, label = AuditEvent.Item.create)
    int createQmPitmInfo(ItemManageVO param);

    @Audit(target = QmPitmInfoSap.class, label = AuditEvent.Item.create)
    int createQmPitmInfoSap(ItemManageVO param);

    @Audit(target = QmPitmAitmSpec.class, label = AuditEvent.Item.createAitemSpec)
    int createQmPitmAitmSpec(ItemApprSpecVO param);

    @Audit(target = QmPitmSpecAitm.class, label = AuditEvent.Item.crateAitem)
    int createQmPitmSpecAitm(ItemApprSpecVO param);

    @Audit(target = QmPitmSpec.class, label = AuditEvent.Item.createSpec)
    int createQmPitmSpec(ItemApprSpecVO param);

    @Audit(target = QmPitm.class, label = AuditEvent.Item.update)
    int updateQmPitmUseVerN(ItemManageVO param);

    int updateSpecNewAitemIdxBySapCode(ItemManageVO param);

    Integer findAItemSpecIdxBySapCode(ItemManageVO param);

    int firstSave(ItemManageVO request);

    Integer saveCtrptNo(ItemManageVO request);

    List<ItemManageVO> getBomList(ItemManageVO ctrptNo);
}
