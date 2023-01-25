package lims.api.an.service;

import lims.api.an.vo.AnalColMaterialVO;

import java.util.List;

public interface AnalColSearchService {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);
    List<AnalColMaterialVO> findAllByRitmMngIdx(Integer ritmMngIdx);
}
