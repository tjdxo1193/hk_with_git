package lims.api.pr.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.pr.entity.QsSmpLabelPrt;
import lims.api.pr.vo.RePrintLabelApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface RePrintLabelApprDao {
    // 라벨재출력승인 조회
    List<RePrintLabelApprVO> findAll(RePrintLabelApprVO param);
    // 라벨재출력승인
    @Audit(target = QsSmpLabelPrt.class, label = AuditEvent.LabelPrint.approveReLabelPrint)
    int approve(RePrintLabelApprVO param);
    // 라벨재출력 반려
    @Audit(target = QsSmpLabelPrt.class, label = AuditEvent.LabelPrint.rejectReLabelPrint)
    int reject(RePrintLabelApprVO param);
}
