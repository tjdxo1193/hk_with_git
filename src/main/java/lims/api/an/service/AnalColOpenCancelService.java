package lims.api.an.service;

import lims.api.an.vo.AnalColMaterialVO;

import java.util.List;

public interface AnalColOpenCancelService {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);
    void cancel(AnalColMaterialVO param);
}