package lims.api.an.service;

import lims.api.an.vo.AnalColMaterialVO;

import java.util.List;

public interface AnalColLabelPrintService {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);
    void update(AnalColMaterialVO param);
}
