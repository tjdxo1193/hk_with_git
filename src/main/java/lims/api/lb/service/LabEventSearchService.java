package lims.api.lb.service;

import lims.api.lb.vo.LabEventSearchVO;

import java.util.List;

public interface LabEventSearchService {
    List<LabEventSearchVO> findAll(LabEventSearchVO dto);
}
