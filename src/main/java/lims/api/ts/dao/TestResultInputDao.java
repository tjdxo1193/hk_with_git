package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sd.entity.RtRitmEtr;
import lims.api.sd.vo.StandardMaterialVO;
import lims.api.ts.entity.QeLabEvt;
import lims.api.ts.entity.QtPitmAnsInfo;
import lims.api.ts.entity.QtPitmAnsProc;
import lims.api.ts.entity.QtPitmAnsRst;
import lims.api.ts.vo.TestResultInputVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface TestResultInputDao {

    List<TestResultInputVO> findAll(TestResultInputVO param);

    List<TestResultInputVO> findResultItem(TestResultInputVO param);

    List<TestResultInputVO> resultHistory(TestResultInputVO param);

    @Audit(target = QtPitmAnsRst.class, label = AuditEvent.TestInfo.inputResult)
    int save(TestResultInputVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.requestReview)
    int requestReview(TestResultInputVO param);

    @Audit(target = QeLabEvt.class, label = AuditEvent.TestInfo.publishLabEvent)
    int publishEvent(TestResultInputVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.savedRequestFile)
    int savedRequestFile(TestResultInputVO param);

    @Audit(target = QtPitmAnsRst.class, label = AuditEvent.TestInfo.savedItemFile)
    int savedItemFile(TestResultInputVO param);

    int getNullCount(TestResultInputVO testResultInputVO);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.inputResult)
    int updateSytJdg(TestResultInputVO testResultInputVO);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.inputResult)
    int updateTestInfo(TestResultInputVO param);

    String getSytJdg(TestResultInputVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.resultInputHold)
    int requestHold(TestResultInputVO param);
}
