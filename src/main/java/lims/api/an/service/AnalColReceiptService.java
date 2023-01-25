package lims.api.an.service;

import lims.api.an.vo.AnalColMaterialVO;

import java.util.List;

public interface AnalColReceiptService {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);
    int create(AnalColMaterialVO param);
    void update(AnalColMaterialVO param);
    void delete(AnalColMaterialVO param);
    int savedFile(AnalColMaterialVO param);
}
