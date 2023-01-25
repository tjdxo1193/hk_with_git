package lims.api.re.service;

import lims.api.re.vo.ReagentMaterialVO;

import java.util.List;

public interface ReagentOpenCancelService {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);
    void cancel(List<ReagentMaterialVO> list);
}