package lims.api.st.service;

import lims.api.st.vo.StabPlanVO;

import java.util.List;
import java.util.Map;

public interface StabPlanService {
    // 안정성시험계획 조회
    List<StabPlanVO> findAll(StabPlanVO param);
    // 안정성시험계획 품목 조회
    List<StabPlanVO> findAllItem(StabPlanVO param);
    // 안정성상세계획 조회
    Map<String, Object> getDetail(StabPlanVO param);
    // 안정성시험계획 저장
    int save(StabPlanVO param);
    // 안정성시험계획 저장, 사이드 이펙트
    int saveSideEffects(StabPlanVO param);
    // 안정성시험계획 수정
    int update(StabPlanVO param);
    // 안정성시험계획 삭제
    int delete(StabPlanVO param);
    // 안정성시험계획 중단용청
    int stopRequest(StabPlanVO param);
    // 안정성시험계획 중단취소 요청
    int stopCancelRequest(StabPlanVO param);
    // 안정성시험계획 승인 요청
    int approveRequest(StabPlanVO param);
}
