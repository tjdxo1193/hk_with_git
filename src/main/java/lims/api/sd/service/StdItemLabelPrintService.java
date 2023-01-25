package lims.api.sd.service;

import lims.api.sd.vo.StandardMaterialVO;

import java.util.List;

public interface StdItemLabelPrintService {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);
    void update(StandardMaterialVO param);
}
