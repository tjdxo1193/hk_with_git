package lims.api.st.dao;


import lims.api.common.domain.AuditEvent;
import lims.api.st.entity.StSbtAns;
import lims.api.st.entity.StSbtAnsAitm;
import lims.api.st.entity.StSbtPln;
import lims.api.st.vo.StabPlanVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import spring.audit.annotation.Audit;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface StabPlanDao {
    List<StabPlanVO> findAll(StabPlanVO param);
    List<StabPlanVO> findAllItem(StabPlanVO param);
    StabPlanVO findOneSyAnsTrm(StabPlanVO param);
    List<StabPlanVO> getAmitm(StabPlanVO param);
    List<StabPlanVO> getHeaderDetail(StabPlanVO param);
    List<String> getAnsDt(StabPlanVO param);
    List<StabPlanVO> getSbtAnsIdx(StabPlanVO param);
    List<Map<String, Object>> getAitmData(StabPlanVO param);
    String findTop1ByOrderBySbtPlnIdxDesc(StabPlanVO param);
    @Audit(target = StSbtPln.class, label = AuditEvent.StabPln.create)
    int save(StabPlanVO param);
    @Audit(target = StSbtAns.class, label = AuditEvent.StabPln.createAns)
    int saveAns(StabPlanVO param);
    @Audit(target = StSbtAns.class, label = AuditEvent.StabPln.updateAns)
    int updateAns(StabPlanVO param);
    @Audit(target = StSbtAnsAitm.class, label = AuditEvent.StabPln.createAitm)
    int saveAitm(StabPlanVO param);
    @Audit(target = StSbtPln.class, label = AuditEvent.StabPln.update)
    int update(StabPlanVO param);
    @Audit(target = StSbtPln.class, label = AuditEvent.StabPln.delete)
    int delete(StabPlanVO param);
    List<StabPlanVO> getAnsForDelete(StabPlanVO param);
    @Audit(target = StSbtAns.class, label = AuditEvent.StabPln.deleteAns)
    int deleteAllAns(StabPlanVO param);
    @Audit(target = StSbtAns.class, label = AuditEvent.StabPln.deleteAns)
    int deleteAns(StabPlanVO param);
    List<StabPlanVO> getAitmForDelete(StabPlanVO param);
    @Audit(target = StSbtAnsAitm.class, label = AuditEvent.StabPln.deleteAitm)
    int deleteAitm(StabPlanVO param);
    @Audit(target = StSbtPln.class, label = AuditEvent.StabPln.stopRequest)
    int stopRequest(StabPlanVO param);
    @Audit(target = StSbtPln.class, label = AuditEvent.StabPln.stopCancelRequest)
    int stopCancelRequest(StabPlanVO param);
    @Audit(target = StSbtPln.class, label = AuditEvent.StabPln.approveRequest)
    int approveRequest(StabPlanVO param);
    @Audit(target = StSbtAnsAitm.class, label = AuditEvent.StabPln.deleteAitm)
    int deleteAitmSideEffect(StabPlanVO param);
}
