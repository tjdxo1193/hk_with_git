package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.QtPitmAnsInfo;
import lims.api.ts.entity.QtPitmAnsProc;
import lims.api.ts.vo.TestCollectionVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface TestCollectionDao {
    List<TestCollectionVO> getTestCollectionList(TestCollectionVO request);

    TestCollectionVO getSmpInfo(TestCollectionVO request);

    int getSmpMngIdx(TestCollectionVO request);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.sampleCollect)
    int collect(TestCollectionVO request);
    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.sampleCollect)
    int updateInfo(TestCollectionVO request);
    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.savedRequestFile)
    int savedRequestFile(TestCollectionVO request);
    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.sampleCollect)
    int save(TestCollectionVO request);
    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.inputSampleManage)
    int insertSmpInfo(TestCollectionVO row);
}
