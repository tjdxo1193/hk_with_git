package lims.api.sy.service;

import lims.api.sy.vo.DepartmentManageVO;

import java.util.List;

public interface DepartmentManageService {
    List<DepartmentManageVO> findAll(DepartmentManageVO vo);
    void insert(DepartmentManageVO vo);
    void update(DepartmentManageVO vo);
}
