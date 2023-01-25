package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.QtPitmAnsProc;
import lims.api.ts.vo.TestRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface TestRequestDao {
    List<TestRequestVO> getTestRequestList(TestRequestVO request);
    List<TestRequestVO> getTestList(TestRequestVO request);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.request)
    int request(TestRequestVO item);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.requestRegist)
    int requestRegist(TestRequestVO request);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.requestRegist)
    int save(TestRequestVO request);

    int getAnsIdx(TestRequestVO request);
}
