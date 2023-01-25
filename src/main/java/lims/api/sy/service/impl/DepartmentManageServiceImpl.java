package lims.api.sy.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sy.dao.DepartmentManageDao;
import lims.api.sy.service.DepartmentManageService;
import lims.api.sy.vo.DepartmentManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentManageServiceImpl implements DepartmentManageService {

    private final DepartmentManageDao dao;

    @Override
    public List<DepartmentManageVO> findAll(DepartmentManageVO vo) {
        return dao.findAll(vo);
    }

    @Override
    public void insert(DepartmentManageVO vo) {
        vo.setDptCd(dao.getNextDeptCode(vo));
        int result = dao.insert(vo);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void update(DepartmentManageVO vo) {
        int result = dao.update(vo);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
