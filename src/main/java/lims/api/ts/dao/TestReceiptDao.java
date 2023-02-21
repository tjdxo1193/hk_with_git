package lims.api.ts.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ts.entity.QtPitmAnsInfo;
import lims.api.ts.entity.QtPitmAnsProc;
import lims.api.ts.entity.QtPitmAnsRst;
import lims.api.ts.vo.TestReceiptVO;
import org.apache.ibatis.annotations.Mapper;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
public interface TestReceiptDao {
    List<TestReceiptVO> getTestReceiptList(TestReceiptVO request);
    List<TestReceiptVO> getTestAitm(TestReceiptVO request);
    int getAnsIdx(String plntCd);
    String getAnsNo(TestReceiptVO request);
    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.receipt)
    int receipt(TestReceiptVO item);
    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.receipt)
    int insertTestProc(TestReceiptVO item);
    @Audit(target = QtPitmAnsInfo.class, label = AuditEvent.TestInfo.receipt)
    int insertTestInfo(TestReceiptVO item);
    @Audit(target = QtPitmAnsRst.class, label = AuditEvent.TestInfo.receipt)
    int insertTestRst(TestReceiptVO item);

    @Audit(target = QtPitmAnsProc.class, label = AuditEvent.TestInfo.receipt)
    int save(TestReceiptVO item);

    List<TestReceiptVO> getNonconformityTestList(TestReceiptVO request);
}
