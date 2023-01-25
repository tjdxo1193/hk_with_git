package lims.api.re.service;

import lims.api.re.vo.ReagentMaterialVO;

import java.util.List;

public interface ReagentUsageHisService {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);
    ReagentMaterialVO create(ReagentMaterialVO param);
    ReagentMaterialVO update(ReagentMaterialVO param);
    void delete(ReagentMaterialVO param);
}