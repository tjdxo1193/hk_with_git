package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.QmPitm;
import lims.api.ms.vo.ItemApprRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Repository
@Mapper
public interface ItemApprRequestDao {
    List<ItemApprRequestVO> find(ItemApprRequestVO param);

    @Audit(target = QmPitm.class, label = AuditEvent.Item.requestApprove)
    int updateRequestApprove(ItemApprRequestVO param);

    @Audit(target = QmPitm.class, label = AuditEvent.Item.rejectReview)
    int updateReturnReview(ItemApprRequestVO param);
}
