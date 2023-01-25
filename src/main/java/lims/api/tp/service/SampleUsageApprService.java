package lims.api.tp.service;

import lims.api.tp.vo.SampleUsageApprVO;

import java.util.List;

public interface SampleUsageApprService {
    List<SampleUsageApprVO> find(SampleUsageApprVO param);

    void approve(List<SampleUsageApprVO> params);

    void reject(List<SampleUsageApprVO> params);
}
