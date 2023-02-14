package lims.api.sc.dao;

import lims.api.common.domain.AuditEvent;
import lims.api.sc.entity.QtPitmAnsPdtPfa;
import lims.api.sc.vo.InspectProductionPerformanceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;

@Mapper
@Repository
public interface InspectProductionPerformanceDao {
    List<InspectProductionPerformanceVO> find(InspectProductionPerformanceVO param);

    List<InspectProductionPerformanceVO> findDetail(String mtrCd, String pdtOrderNo, String batchNo, String etrDt);

    List<InspectProductionPerformanceVO> getRecord(Integer ispPdtPfaIdx);

    int findIspPdtPfaIdx();

    @Audit(target = QtPitmAnsPdtPfa.class, label = AuditEvent.InspectionProductionPerformance.createRecord)
    int insertPdtRecord(InspectProductionPerformanceVO param);

    @Audit(target = QtPitmAnsPdtPfa.class, label = AuditEvent.InspectionProductionPerformance.updateState)
    int updatePdtIdx(InspectProductionPerformanceVO param);
}
