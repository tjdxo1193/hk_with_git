package lims.api.common.service.impl;

import lims.api.common.dao.MenuDao;
import lims.api.common.service.MenuService;
import lims.api.sy.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;

    @Override
    public List<MenuVO> getMenus() {
        return menuDao.findUseMenus();
    }
}