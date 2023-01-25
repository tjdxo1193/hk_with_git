package lims.api.st.service;

import lims.api.st.vo.StabPlanDetailApprVO;

import java.util.List;
import java.util.Map;

public interface StabPlanDetailApprService {
    // 안정성상세계획 승인 조회
    List<StabPlanDetailApprVO> findAll(StabPlanDetailApprVO param);
    // 안정성상세계획 조회
    Map<String, List<StabPlanDetailApprVO>> getDetail(StabPlanDetailApprVO param);

    // 안정성시험계획 승인
    int approve(List<StabPlanDetailApprVO> param);
    int approve(StabPlanDetailApprVO param);
    // 안정성시험계획 반려
    int reject(List<StabPlanDetailApprVO> param);
    int reject(StabPlanDetailApprVO param);
}
