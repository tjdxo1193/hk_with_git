package lims.api.kp.service;

import lims.api.kp.vo.AddSampleSearchVO;

import java.util.List;

public interface AddSampleSearchService {
    List<AddSampleSearchVO> find(AddSampleSearchVO param);
}
