package lims.api.an.service;

import lims.api.an.vo.AnalColMaterialVO;

import java.util.List;

public interface AnalColUsageHisService {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);
    List<AnalColMaterialVO> findOpenItem(AnalColMaterialVO param);
    int create(AnalColMaterialVO param);
    void update(AnalColMaterialVO param);
    void delete(AnalColMaterialVO param);
}