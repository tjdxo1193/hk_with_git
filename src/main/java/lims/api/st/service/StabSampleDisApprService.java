package lims.api.st.service;

import lims.api.st.vo.StabSampleDisApprVO;

import java.util.List;

public interface StabSampleDisApprService {
    List<StabSampleDisApprVO> find(StabSampleDisApprVO param);

    void approve(List<StabSampleDisApprVO> params);

    void reject(List<StabSampleDisApprVO> params);

}
