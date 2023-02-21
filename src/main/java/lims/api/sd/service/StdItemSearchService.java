package lims.api.sd.service;

import lims.api.sd.vo.StandardMaterialVO;

import java.util.List;

public interface StdItemSearchService {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);
    List<StandardMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx);
    List<StandardMaterialVO> findAllByRitmMngIdx(Integer ritmEtrIdx, Integer ritmMngIdx, String delYn);
}
