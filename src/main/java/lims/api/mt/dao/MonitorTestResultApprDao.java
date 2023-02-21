package lims.api.mt.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.mt.entity.MtMitmAnsProc;
import lims.api.mt.vo.MonitorTestResultApprVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface MonitorTestResultApprDao {
    List<MonitorTestResultApprVO> getMonitorTestResultApprList(MonitorTestResultApprVO request);
    List<MonitorTestResultApprVO> getMonitorTestRst(MonitorTestResultApprVO request);

    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.testApprove)
    int approve(MonitorTestResultApprVO request);
    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.apprReject)
    int reject(MonitorTestResultApprVO request);
    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.testHold)
    int hold(MonitorTestResultApprVO request);
}
