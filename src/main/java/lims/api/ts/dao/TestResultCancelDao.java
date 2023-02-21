package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.QtPitmAnsInfo;
import lims.api.ts.entity.QtPitmAnsProc;
import lims.api.ts.entity.QtPitmAnsRst;
import lims.api.ts.vo.TestResultCancelVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface TestResultCancelDao {

    List<TestResultCancelVO> findAll(TestResultCancelVO param);

    List<TestResultCancelVO> findResultItem(Integer ansIdx);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.testCancel)
    int testCancel(TestResultCancelVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.testHold)
    int hold(TestResultCancelVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.testHoldOff)
    int holdOff(TestResultCancelVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.savedRequestFile)
    int savedRequestFile(TestResultCancelVO param);


    @Audit(target = QtPitmAnsRst.class, label = AuditEvent.TestInfo.savedItemFile)
    int savedItemFile(TestResultCancelVO param);
}
