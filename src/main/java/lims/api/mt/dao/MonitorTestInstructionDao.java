package lims.api.mt.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.mt.entity.MtMitmAnsProc;
import lims.api.mt.entity.MtMitmAnsRst;
import lims.api.mt.vo.MonitorTestInstructionVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface MonitorTestInstructionDao {
    List<MonitorTestInstructionVO> getMonitorTestInstructionList(MonitorTestInstructionVO request);

    List<MonitorTestInstructionVO> getMonitorTestRst(MonitorTestInstructionVO request);

    String getAssNo(String plntCd);

    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.testInstruction)
    int instruct(MonitorTestInstructionVO request);
    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.resultInput)
    int insertVrib(MonitorTestInstructionVO request);
    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.resultDelete)
    int deleteRst(MonitorTestInstructionVO item);

}
