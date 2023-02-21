package lims.api.sd.service;

import lims.api.sd.vo.StandardMaterialVO;

import java.util.List;

public interface StdItemOpenDisService {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);
    void open(List<StandardMaterialVO> list);
    void requestDisposal(List<StandardMaterialVO> list);
}