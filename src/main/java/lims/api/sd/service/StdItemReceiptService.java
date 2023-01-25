package lims.api.sd.service;

import lims.api.sd.vo.StandardMaterialVO;

import java.util.List;

public interface StdItemReceiptService {
    List<StandardMaterialVO> findAll(StandardMaterialVO param);
    int create(StandardMaterialVO param);
    void update(StandardMaterialVO param);
    void delete(StandardMaterialVO param);
    void requestApprove(List<StandardMaterialVO> list);
    int savedFile(StandardMaterialVO param);
}
