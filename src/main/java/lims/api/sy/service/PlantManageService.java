package lims.api.sy.service;

import lims.api.sy.vo.PlantManageVO;

import java.util.List;

public interface PlantManageService {

    List<PlantManageVO> getPlants(PlantManageVO param);

    void create(PlantManageVO param);

    void update(PlantManageVO param);

}