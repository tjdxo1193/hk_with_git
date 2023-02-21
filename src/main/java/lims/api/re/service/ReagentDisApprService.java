package lims.api.re.service;

import lims.api.re.vo.ReagentMaterialVO;

import java.util.List;

public interface ReagentDisApprService {
    List<ReagentMaterialVO> findAll(ReagentMaterialVO param);
    void approve(List<ReagentMaterialVO> list);
    void reject(List<ReagentMaterialVO> list);
}