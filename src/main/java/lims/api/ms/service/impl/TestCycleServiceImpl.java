package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ms.dao.TestCycleDao;
import lims.api.ms.service.TestCycleService;
import lims.api.ms.vo.TestCycleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCycleServiceImpl implements TestCycleService {

    private final TestCycleDao testCycleDao;

    @Override
    public List<TestCycleVO> getList(TestCycleVO param) {
        return testCycleDao.getList(param);
    }

    @Override
    public void create(TestCycleVO param) {
        int result = testCycleDao.create(param);

        if (result == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(TestCycleVO param) {
        int result = testCycleDao.update(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(TestCycleVO param) {
        int result = testCycleDao.delete(param);

        if (result == 0) {
            throw new NoDeletedDataException();
        }
    }
}