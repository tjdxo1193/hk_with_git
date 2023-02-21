package lims.api.sd.service;

import lims.api.sd.vo.StandardMaterialVO;

import java.util.List;

public interface StdItemDisApprService {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);
    void approve(List<StandardMaterialVO> list);
    void reject(List<StandardMaterialVO> list);
}