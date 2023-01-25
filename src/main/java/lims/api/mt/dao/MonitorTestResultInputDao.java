package lims.api.mt.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.mt.entity.MtMitmAnsProc;
import lims.api.mt.entity.MtMitmAnsRst;
import lims.api.mt.vo.MonitorTestResultInputVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface MonitorTestResultInputDao {
    List<MonitorTestResultInputVO> getMonitorTestResultInputList(MonitorTestResultInputVO request);
    List<MonitorTestResultInputVO> getMonitorTestRst(MonitorTestResultInputVO request);

    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.resultReview)
    int requestReview(MonitorTestResultInputVO request);
    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.resultInput)
    int updateRst(MonitorTestResultInputVO item);

    int getNullCount(MonitorTestResultInputVO request);

    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.inputResult)
    int updateSytJdg(MonitorTestResultInputVO request);

    String getSytJdg(MonitorTestResultInputVO request);
}
