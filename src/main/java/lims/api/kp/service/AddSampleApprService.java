package lims.api.kp.service;

import lims.api.kp.vo.AddSampleApprVO;

import java.util.List;

public interface AddSampleApprService {
    List<AddSampleApprVO> find(AddSampleApprVO param);

    void approve(AddSampleApprVO param);

    void reject(AddSampleApprVO param);
}
