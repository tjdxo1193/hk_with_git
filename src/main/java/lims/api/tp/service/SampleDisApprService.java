package lims.api.tp.service;

import lims.api.tp.vo.SampleDisApprVO;

import java.util.List;

public interface SampleDisApprService {
    List<SampleDisApprVO> find(SampleDisApprVO param);

    void approve(List<SampleDisApprVO> params);

    void reject(List<SampleDisApprVO> params);
}
