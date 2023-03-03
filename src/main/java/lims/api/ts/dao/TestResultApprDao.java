package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.QtPitmAnsInfo;
import lims.api.ts.entity.QtPitmAnsProc;
import lims.api.ts.entity.QtPitmAnsRst;
import lims.api.ts.vo.TestResultApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface TestResultApprDao {

    List<TestResultApprVO> findAll(TestResultApprVO param);

    List<TestResultApprVO> findResultItem(TestResultApprVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.testFinalApproved)
    int approve(TestResultApprVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.requestApproveHold)
    int requestHold(TestResultApprVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.reviewRejection)
    int requestRejection(TestResultApprVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.testJudgeModify)
    int save(TestResultApprVO request);
}
