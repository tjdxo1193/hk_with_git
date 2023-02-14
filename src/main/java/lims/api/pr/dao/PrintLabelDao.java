package lims.api.pr.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.pr.entity.QsSmpLabelPrt;
import lims.api.pr.entity.SyRptMst;
import lims.api.pr.vo.PrintLabelVO;
import lims.api.ts.entity.QtPitmAnsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface PrintLabelDao {
    // 라벨출력 조회
    List<PrintLabelVO> findAll(PrintLabelVO param);
    // 라벨출력 생성
    @Audit(target = QsSmpLabelPrt.class, label = AuditEvent.LabelPrint.createLabelPrint)
    int createQsSmpLabelPrt(PrintLabelVO param);
    // 리포트 마스터 생성
    @Audit(target = SyRptMst.class, label = AuditEvent.RptMst.create)
    int createSyRptMst(PrintLabelVO param);
    // 품목 시험 정보 수정
    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.update)
    int updateQtPitmAnsInfoSmpLabelPrtIdx(PrintLabelVO param);
}
