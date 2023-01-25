package lims.api.sd.service;

import lims.api.sd.vo.StandardMaterialVO;

import java.util.List;

public interface StdItemOpenCancelService {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);
    void cancel(List<StandardMaterialVO> list);
}