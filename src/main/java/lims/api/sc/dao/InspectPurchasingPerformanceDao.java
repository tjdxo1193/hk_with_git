package lims.api.sc.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sc.entity.QtPitmAnsPhsPfa;
import lims.api.sc.vo.InspectPurchasingPerformanceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface InspectPurchasingPerformanceDao {

    List<InspectPurchasingPerformanceVO> find(InspectPurchasingPerformanceVO param);

    List<InspectPurchasingPerformanceVO> findDetail(String plntCd, String pitmCd, String phsOrderNo);

    List<InspectPurchasingPerformanceVO> getRecord(Integer ispPhsPfaIdx);

    int findIspPhsPfaIdx();

    @Audit(target = QtPitmAnsPhsPfa.class, label = AuditEvent.InspectionPurchasingPerformance.createRecord)
    int insertPhsRecord(InspectPurchasingPerformanceVO param);

    @Audit(target = QtPitmAnsPhsPfa.class, label = AuditEvent.InspectionPurchasingPerformance.updateState)
    int updatePhsIdx(InspectPurchasingPerformanceVO param);
}
