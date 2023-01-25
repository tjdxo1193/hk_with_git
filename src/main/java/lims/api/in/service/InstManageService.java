package lims.api.in.service;

import lims.api.in.vo.InstManageVO;

import java.util.List;

public interface InstManageService {
    List<InstManageVO> find(InstManageVO param);

    List<InstManageVO> findAccessory(InstManageVO param);

    void create(InstManageVO param);

    void update(InstManageVO param);

    void delete(InstManageVO param);

    Integer updateFile(InstManageVO param);
}
