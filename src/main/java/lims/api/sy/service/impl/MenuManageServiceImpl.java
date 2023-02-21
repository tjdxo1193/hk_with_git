package lims.api.sy.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sy.dao.MenuManageDao;
import lims.api.sy.service.MenuManageService;
import lims.api.sy.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuManageServiceImpl implements MenuManageService {

    private final MenuManageDao dao;

    @Override
    public List<MenuVO> findAll(MenuVO param) {
        return dao.findAll(param);
    }

    @Override
    public void update(MenuVO param) {
        int result = dao.update(param);
        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

}