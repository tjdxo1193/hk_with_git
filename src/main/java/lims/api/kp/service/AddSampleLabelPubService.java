package lims.api.kp.service;

import lims.api.kp.vo.AddSampleLabelPubVO;

import java.util.List;

public interface AddSampleLabelPubService {
    List<AddSampleLabelPubVO> find(AddSampleLabelPubVO param);
}
