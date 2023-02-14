package lims.api.st.service;

import lims.api.st.vo.StabSampleUsageVO;

import java.util.List;

public interface StabSampleUsageService {
    List<StabSampleUsageVO> find(StabSampleUsageVO param);

    void requestApproveUse(StabSampleUsageVO param);

    void create(StabSampleUsageVO param);

    void update(StabSampleUsageVO param);

    void delete(StabSampleUsageVO param);

    void requestCancelUse(StabSampleUsageVO param);

    List<StabSampleUsageVO> findSample(StabSampleUsageVO param);
}
