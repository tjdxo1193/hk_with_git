package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ms.dao.MonitorManageDao;
import lims.api.ms.service.MonitorManageService;
import lims.api.ms.vo.MonitorManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorManageServiceImpl implements MonitorManageService {
    private final MonitorManageDao dao;

    @Override
    public List<MonitorManageVO> find(MonitorManageVO param) {
        return dao.find(param);
    }

    @Override
    public void insert(MonitorManageVO param) {
        int result = dao.insert(param);
        if (result == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(MonitorManageVO param) {
        int result = dao.update(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(MonitorManageVO param) {
        int result = dao.delete(param);
        if (result == 0) {
            throw new NoDeletedDataException();
        }
    }
}
