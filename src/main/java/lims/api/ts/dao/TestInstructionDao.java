package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.QtPitmAnsInfo;
import lims.api.ts.entity.QtPitmAnsProc;
import lims.api.ts.entity.QtPitmAnsRst;
import lims.api.ts.vo.TestInstructionVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface TestInstructionDao {
    List<TestInstructionVO> getTestInstructList(TestInstructionVO request);
    List<TestInstructionVO> getTestAitm(TestInstructionVO request);

    String getAssNo(String plntCd);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.testInstruction)
    int instruct(TestInstructionVO item);
    @Audit(target = QtPitmAnsRst.class, label = AuditEvent.TestInfo.resultDelete)
    int deleteRst(TestInstructionVO request);
    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.savedRequestFile)
    int savedRequestFile(TestInstructionVO request);
}
