package lims.api.an.service;

import lims.api.an.vo.AnalColMaterialVO;

import java.util.List;

public interface AnalColOpenDisService {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);
    void open(AnalColMaterialVO param);
    void update(AnalColMaterialVO param);
    void disposal(AnalColMaterialVO param);
}