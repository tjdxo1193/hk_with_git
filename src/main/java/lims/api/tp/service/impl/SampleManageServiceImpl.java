package lims.api.tp.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.tp.dao.SampleManageDao;
import lims.api.tp.enums.SampleDisposalProgress;
import lims.api.tp.service.SampleManageService;
import lims.api.tp.vo.SampleManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleManageServiceImpl implements SampleManageService {
    private final SampleManageDao dao;

    @Override
    public List<SampleManageVO> find(SampleManageVO param) {
        return dao.find(param);
    }

    @Override
    public void create(SampleManageVO param) {
        int result = 0;
        param.setSmpDpsProc(SampleDisposalProgress.SAMPLE_STORED.getCode());
        result += dao.create(param);
        if (result < 1) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(SampleManageVO param) {
        int result = 0;
        result += dao.update(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(SampleManageVO param) {
        int result = 0;
        result += dao.delete(param);
        if (result < 1) {
            throw new NoDeletedDataException();
        }

    }

    @Override
    public List<SampleManageVO> findTest(SampleManageVO param) {
        return dao.findTest(param);
    }
}
