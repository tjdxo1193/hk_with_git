package lims.api.sm.service;

import lims.api.sm.vo.SpecimenManageVO;

import java.util.List;

public interface SpecimenManageService {
    List<SpecimenManageVO> getSpecimenList(SpecimenManageVO request);
    List<SpecimenManageVO> getSpecimenHis(SpecimenManageVO request);

    void save(SpecimenManageVO request);
}
