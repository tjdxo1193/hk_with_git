package lims.api.pr.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.pr.entity.QsSmpLabelPrt;
import lims.api.pr.entity.SyRptMst;
import lims.api.pr.vo.RePrintLabelRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface RePrintLabelRequestDao {
    // 라벨재출력요청 조회
    List<RePrintLabelRequestVO> findAll(RePrintLabelRequestVO param);
    // 라벨재출력요청 모달 조회
    List<RePrintLabelRequestVO> findLabelList(RePrintLabelRequestVO param);
    // 라벨재출력요청
    @Audit(target = QsSmpLabelPrt.class, label = AuditEvent.LabelPrint.requestReLabelPrint)
    int rePrintLabelRequest(RePrintLabelRequestVO param);
    // 라벨 리포트 구분 수정
    @Audit(target = SyRptMst.class, label = AuditEvent.LabelPrint.requestReLabelPrint)
    int updateSyRptMst(RePrintLabelRequestVO param);
    // 라벨재출력취소
    @Audit(target = QsSmpLabelPrt.class, label = AuditEvent.LabelPrint.cancelReLabelPrint)
    int rePrintLabelCancel(RePrintLabelRequestVO param);

}
