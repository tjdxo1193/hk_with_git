package lims.api.tp.service;

import lims.api.tp.vo.SampleDisVO;

import java.util.List;

public interface SampleDisService {
    List<SampleDisVO> find(SampleDisVO param);

    void requestDisposal(List<SampleDisVO> params);

    void requestDisposalCancel(List<SampleDisVO> params);
}
