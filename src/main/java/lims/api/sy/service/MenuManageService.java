package lims.api.sy.service;


import lims.api.sy.vo.MenuVO;

import java.util.List;

public interface MenuManageService {
    List<MenuVO> findAll(MenuVO param);
    void update(MenuVO param);
}