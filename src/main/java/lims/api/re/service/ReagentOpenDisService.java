package lims.api.re.service;

import lims.api.re.vo.ReagentMaterialVO;

import java.util.List;

public interface ReagentOpenDisService {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);
    void open(List<ReagentMaterialVO> list);
    void requestDisposal(List<ReagentMaterialVO> list);
}