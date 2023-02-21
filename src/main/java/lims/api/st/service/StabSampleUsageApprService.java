package lims.api.st.service;

import lims.api.st.vo.StabSampleUsageApprVO;

import java.util.List;

public interface StabSampleUsageApprService {
    List<StabSampleUsageApprVO> find(StabSampleUsageApprVO param);

    void approve(StabSampleUsageApprVO param);

    void reject(StabSampleUsageApprVO param);
}
