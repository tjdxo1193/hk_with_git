package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.QtPitmAnsInfo;
import lims.api.ts.entity.QtPitmAnsProc;
import lims.api.ts.entity.QtPitmAnsRst;
import lims.api.ts.vo.TestResultReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface TestResultReviewDao {

    List<TestResultReviewVO> findAll(TestResultReviewVO param);

    List<TestResultReviewVO> findResultItem(TestResultReviewVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.completedReview)
    int completedReview(TestResultReviewVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.requestReviewHold)
    int requestHold(TestResultReviewVO param);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.reviewRejection)
    int requestRejection(TestResultReviewVO param);

    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.savedRequestFile)
    int savedRequestFile(TestResultReviewVO param);

    @Audit(target = QtPitmAnsRst.class, label = AuditEvent.TestInfo.savedItemFile)
    int savedItemFile(TestResultReviewVO param);
}
