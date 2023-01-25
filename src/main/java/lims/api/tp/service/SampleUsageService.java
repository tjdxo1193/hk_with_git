package lims.api.tp.service;

import lims.api.tp.vo.SampleUsageVO;

import java.util.List;

public interface SampleUsageService {
    List<SampleUsageVO> find(SampleUsageVO param);

    List<SampleUsageVO> findSample(SampleUsageVO param);

    void requestApproveUsage(SampleUsageVO param);

    void create(SampleUsageVO param);

    void update(SampleUsageVO param);

    void delete(SampleUsageVO param);

    void requestCancelUsage(SampleUsageVO param);
}
