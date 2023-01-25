package lims.api.ms.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.ms.entity.SyAnsTrm;
import lims.api.ms.entity.SyAnsTrmItv;
import lims.api.ms.vo.TestTermInTerValManageVO;
import lims.api.ms.vo.TestTermManageCodeVO;
import lims.api.ms.vo.TestTermManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface TestTermManageDao {
    List<TestTermManageVO> getTermList(TestTermManageVO param);

    List<TestTermInTerValManageVO> getIntervalList(TestTermManageVO param);

    @Audit(target = SyAnsTrm.class, label = AuditEvent.TestTerm.createTerm)
    int createTerm(TestTermManageVO param);

    @Audit(target = SyAnsTrm.class, label = AuditEvent.TestTerm.updateTerm)
    int updateTerm(TestTermManageVO param);

    @Audit(target = SyAnsTrm.class, label = AuditEvent.TestTerm.deleteTerm)
    int deleteTerm(TestTermManageVO param);

    @Audit(target = SyAnsTrmItv.class, label = AuditEvent.TestTerm.createInterval)
    int createInterval(TestTermInTerValManageVO param);

    @Audit(target = SyAnsTrmItv.class, label = AuditEvent.TestTerm.deleteInterval)
    int deleteInterval(TestTermInTerValManageVO param);

    String getCurrentAnsTrmCd(String plntCd);

    List<TestTermManageCodeVO> getTestCycleDivision(TestTermManageCodeVO param);
}