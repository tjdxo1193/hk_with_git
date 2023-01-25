package lims.api.mt.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.mt.entity.MtMitmAnsProc;
import lims.api.mt.entity.MtMitmAnsRst;
import lims.api.mt.vo.MonitorTestReceiptVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface MonitorTestReceiptDao {
    //조회
    List<MonitorTestReceiptVO> getMonitorTestReceiptList(MonitorTestReceiptVO request);
    List<MonitorTestReceiptVO> getMonitorTestMitm(MonitorTestReceiptVO request);

    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.receipt)
    int receipt(MonitorTestReceiptVO request);

    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.resultInput)
    int insertRst(MonitorTestReceiptVO request);
}
