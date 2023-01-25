package lims.api.in.service;


import lims.api.in.vo.InstHisApprVO;

import java.util.List;

public interface InstHisApprService {
    List<InstHisApprVO> findAll(InstHisApprVO param);
    List<InstHisApprVO> findByHisSeq(Integer hisSeq);
    void approve(List<InstHisApprVO> list);
    void reject(List<InstHisApprVO> list);
}
