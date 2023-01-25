package lims.api.ts.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ts.dao.TestUnsuitableListDao;
import lims.api.ts.enums.TestJudgement;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.service.TestUnsuitableListService;
import lims.api.ts.vo.TestUnsuitableListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestUnsuitableListServiceImpl implements TestUnsuitableListService {

    private final TestUnsuitableListDao dao;

    @Override
    public List<TestUnsuitableListVO> findAll(TestUnsuitableListVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        param.setSytJdg(TestJudgement.UNSUITABLE.getJudgementCode());
        return dao.findAll(param);
    }

    @Override
    public List<TestUnsuitableListVO> findResultItem(Integer ansIdx) {
        TestUnsuitableListVO param = new TestUnsuitableListVO();
        param.setAnsIdx(ansIdx);
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return dao.findResultItem(param);
    }

    @Override
    public void save(TestUnsuitableListVO param) {
        //TODO 부적합사유 UPDATE
        int result = dao.save(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void publish(TestUnsuitableListVO param) {
        //TODO 부적합사유 UPDATE
        int result = dao.save(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
