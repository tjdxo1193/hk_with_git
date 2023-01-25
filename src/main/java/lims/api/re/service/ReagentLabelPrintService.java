package lims.api.re.service;

import lims.api.re.vo.ReagentMaterialVO;

import java.util.List;

public interface ReagentLabelPrintService {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);
    void update(ReagentMaterialVO param);
}
