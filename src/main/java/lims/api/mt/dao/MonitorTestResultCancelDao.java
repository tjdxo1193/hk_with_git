package lims.api.mt.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.mt.entity.MtMitmAnsRst;
import lims.api.mt.vo.MonitorTestResultCancelVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface MonitorTestResultCancelDao {
    List<MonitorTestResultCancelVO> getMonitorTestResultCancelList(MonitorTestResultCancelVO request);
    List<MonitorTestResultCancelVO> getMonitorTestRst(MonitorTestResultCancelVO request);

    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.testCancel)
    int cancel(MonitorTestResultCancelVO request);
    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.testHold)
    int holdnOff(MonitorTestResultCancelVO request);
}
