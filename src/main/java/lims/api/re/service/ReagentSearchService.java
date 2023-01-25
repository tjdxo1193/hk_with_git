package lims.api.re.service;

import lims.api.re.vo.ReagentMaterialVO;

import java.util.List;

public interface ReagentSearchService {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);
    List<ReagentMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx);
    List<ReagentMaterialVO> findAllByRitmMngIdx(Integer ritmEtrIdx, Integer ritmMngIdx, String delYn);
}
