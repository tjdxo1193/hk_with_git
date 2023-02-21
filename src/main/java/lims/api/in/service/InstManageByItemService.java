package lims.api.in.service;

import lims.api.in.vo.InstManageByItemVO;

import java.util.List;

public interface InstManageByItemService {
    List<InstManageByItemVO> findItem(InstManageByItemVO param);

    List<InstManageByItemVO> findDetail(InstManageByItemVO param);

}
