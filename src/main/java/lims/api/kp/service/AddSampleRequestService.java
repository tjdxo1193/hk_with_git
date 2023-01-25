package lims.api.kp.service;

import lims.api.kp.vo.AddSampleRequestVO;

import java.util.List;

public interface AddSampleRequestService {
    List<AddSampleRequestVO> find(AddSampleRequestVO param);

    void create(AddSampleRequestVO param);

    void update(AddSampleRequestVO param);

    void delete(AddSampleRequestVO param);

    void requestApprove(AddSampleRequestVO param);

    String makeLabelCd(AddSampleRequestVO param);
}
