package lims.api.st.service;

import lims.api.st.vo.StabSampleDisVO;

import java.util.List;

public interface StabSampleDisService {
    List<StabSampleDisVO> find(StabSampleDisVO param);

    void requestDispose(List<StabSampleDisVO> params);

    void requestCancelDispose(List<StabSampleDisVO> params);
}
