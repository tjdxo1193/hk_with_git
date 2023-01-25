package lims.api.sd.service;

import lims.api.sd.vo.StandardMaterialVO;

import java.util.List;

public interface StdItemUsageHisService {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);
    List<StandardMaterialVO> findOpenItem(StandardMaterialVO param);
    StandardMaterialVO create(StandardMaterialVO param);
    StandardMaterialVO update(StandardMaterialVO param);
    void delete(StandardMaterialVO param);
}