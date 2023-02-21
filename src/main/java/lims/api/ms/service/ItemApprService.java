package lims.api.ms.service;

import lims.api.ms.vo.ItemManageVO;

import java.util.List;

public interface ItemApprService {
    List<ItemManageVO> find(ItemManageVO param);

    void approve(List<ItemManageVO> params);

    void reject(List<ItemManageVO> params);
}
