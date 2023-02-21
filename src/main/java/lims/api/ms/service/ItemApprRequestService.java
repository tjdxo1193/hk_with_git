package lims.api.ms.service;

import lims.api.ms.vo.ItemApprRequestVO;
import lims.api.ms.vo.ItemManageVO;

import java.util.List;

public interface ItemApprRequestService {
    List<ItemApprRequestVO> find(ItemApprRequestVO param);

    void requestApprove(ItemApprRequestVO param);

    void returnReview(ItemApprRequestVO param);
}
