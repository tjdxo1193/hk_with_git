package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ms.dao.TestMaterialManageDao;
import lims.api.ms.service.TestMaterialManageService;
import lims.api.ms.vo.TestMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestMaterialManageServiceImpl implements TestMaterialManageService {

    private final TestMaterialManageDao manageDao;

    @Override
    public List<TestMaterialVO> getMasters(TestMaterialVO param) {
        return manageDao.findAll(param);
    }

    @Override
    public TestMaterialVO create(TestMaterialVO param) {
        int result = manageDao.create(param);

        if (result == 0) {
            throw new NoCreatedDataException();
        }

        return param;
    }

    @Override
    public void update(TestMaterialVO param) {
        int result = manageDao.update(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(TestMaterialVO param) {
        int result = manageDao.delete(param);

        if (result == 0) {
            throw new NoDeletedDataException();
        }
    }
}