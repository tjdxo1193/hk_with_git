package lims.api.mt.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.mt.entity.MtMitmAnsProc;
import lims.api.mt.vo.MonitorTestRequestVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface MonitorTestRequestDao {
    List<MonitorTestRequestVO> getMonitorTestRequestList(MonitorTestRequestVO request);

    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.request)
    int request(MonitorTestRequestVO request);

    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.requestCancel)
    int requestCancel(MonitorTestRequestVO item);
}
