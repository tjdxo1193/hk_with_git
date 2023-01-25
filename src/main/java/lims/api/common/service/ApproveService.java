package lims.api.common.service;

import lims.api.common.vo.ApproveVO;

public interface ApproveService {
    void create(ApproveVO param);
    void update(ApproveVO param);
    void approve(Integer aprIdx);
    Integer requestApprove(ApproveVO param);
}
