package lims.api.mt.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.mt.entity.MtMitmAnsProc;
import lims.api.mt.entity.MtMitmAnsRst;
import lims.api.mt.vo.MonitorTestResultReviewVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface MonitorTestResultReviewDao {
    List<MonitorTestResultReviewVO> getMonitorTestResultReviewList(MonitorTestResultReviewVO request);
    List<MonitorTestResultReviewVO> getMonitorTestRst(MonitorTestResultReviewVO request);

    @Audit(target = MtMitmAnsProc.class, label = AuditEvent.TestInfo.resultReview)
    int apprRequest(MonitorTestResultReviewVO request);
    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.reviewReject)
    int reject(MonitorTestResultReviewVO request);
    @Audit(target = MtMitmAnsRst.class, label = AuditEvent.TestInfo.testHold)
    int hold(MonitorTestResultReviewVO request);
}
