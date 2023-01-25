package lims.api.re.service;

import lims.api.re.vo.ReagentMaterialVO;

import java.util.List;

public interface ReagentReceiptService {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);
    int create(ReagentMaterialVO param);
    void update(ReagentMaterialVO param);
    void delete(ReagentMaterialVO param);
    int savedFile(ReagentMaterialVO param);
}
