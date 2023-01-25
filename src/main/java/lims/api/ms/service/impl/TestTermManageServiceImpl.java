package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ms.dao.TestTermManageDao;
import lims.api.ms.enums.CycleDivision;
import lims.api.ms.service.TestTermManageService;
import lims.api.ms.vo.TestTermInTerValManageVO;
import lims.api.ms.vo.TestTermManageCodeVO;
import lims.api.ms.vo.TestTermManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestTermManageServiceImpl implements TestTermManageService {

    private final TestTermManageDao testTermManageDao;

    @Override
    public List<TestTermManageVO> getTermList(TestTermManageVO param) {
        return testTermManageDao.getTermList(param);
    }

    @Override
    public List<TestTermInTerValManageVO> getIntervalList(TestTermManageVO param) {
        return testTermManageDao.getIntervalList(param);
    }

    @Override
    public void createTerm(TestTermManageVO param) {
        int result = testTermManageDao.createTerm(param);

        if (result == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void updateTerm(TestTermManageVO param) {
        int result = testTermManageDao.updateTerm(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void deleteTerm(TestTermManageVO param) {
        int result = testTermManageDao.deleteTerm(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void createInterval(List<TestTermInTerValManageVO> param) {
        testTermManageDao.deleteInterval(param.get(0));

        int isCreateCompleted = 0;

        for(TestTermInTerValManageVO tvo : param){
            isCreateCompleted = testTermManageDao.createInterval(tvo);

            if (isCreateCompleted == 0) {
                throw new NoCreatedDataException();
            }
        }

    }

    @Override
    public void deleteInterval(TestTermInTerValManageVO param) {
        int result = testTermManageDao.deleteInterval(param);

        if (result == 0) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public List<TestTermManageCodeVO> getTestCycleDivision(TestTermManageCodeVO param) {
        return testTermManageDao.getTestCycleDivision(param);
    }

    @Override
    public void createInitInterval(TestTermManageVO param){
        int result = 0;

        TestTermInTerValManageVO ivo = new TestTermInTerValManageVO();
        CycleDivision cylDiv = CycleDivision.valueOfCode(param.getAnsCylDiv());

        ivo.setPlntCd(param.getPlntCd());
        ivo.setAnsTrmCd(testTermManageDao.getCurrentAnsTrmCd(param.getPlntCd()));

        for(int i = 1, currentItv = i*param.getAnsItv(); currentItv <= param.getAnsTrm() ; i++, currentItv = i*param.getAnsItv()  ) {
            ivo.setAccMarkNm(Integer.toString(currentItv) + cylDiv.getEngName());
            ivo.setAccRulNm(Integer.toString(currentItv) + cylDiv.getRuleName());
            ivo.setAnsAccVal(currentItv);

            result = testTermManageDao.createInterval(ivo);

            if(result == 0){
                throw new NoCreatedDataException();
            }
        }

        if(result == 0){
            throw new NoCreatedDataException();
        }
    }
}