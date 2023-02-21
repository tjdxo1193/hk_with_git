package lims.api.an.service;

import lims.api.an.vo.AnalColMaterialVO;

import java.util.List;

public interface AnalColDisApprService {
    List<AnalColMaterialVO> findAll(AnalColMaterialVO param);
    void approve(List<AnalColMaterialVO> list);
    void reject(List<AnalColMaterialVO> list);
}