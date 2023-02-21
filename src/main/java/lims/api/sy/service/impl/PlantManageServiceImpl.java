package lims.api.sy.service.impl;

import lims.api.sy.dao.PlantManageDao;
import lims.api.sy.service.PlantManageService;
import lims.api.sy.vo.PlantManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantManageServiceImpl implements PlantManageService {

    private final PlantManageDao plantManageDao;

    @Override
    public List<PlantManageVO> getPlants(PlantManageVO param) {
        return plantManageDao.findAll(param);
    }

    @Override
    public void create(PlantManageVO param) {
        String code = plantManageDao.nextCode();
        param.setPlntCd(code);
        plantManageDao.create(param);
    }

    @Override
    public void update(PlantManageVO param) {
        plantManageDao.update(param);
    }

}