package lims.api.ms.service;

import lims.api.ms.vo.TestTermInTerValManageVO;
import lims.api.ms.vo.TestTermManageCodeVO;
import lims.api.ms.vo.TestTermManageVO;

import java.util.List;

public interface TestTermManageService {
    List<TestTermManageVO> getTermList(TestTermManageVO param);
    List<TestTermInTerValManageVO> getIntervalList(TestTermManageVO param);

    List<TestTermManageCodeVO> getTestCycleDivision(TestTermManageCodeVO param);

    void createTerm(TestTermManageVO param);
    void updateTerm(TestTermManageVO param);
    void deleteTerm(TestTermManageVO param);
    void createInitInterval(TestTermManageVO param);
    void createInterval(List<TestTermInTerValManageVO> param);

    void deleteInterval(TestTermInTerValManageVO param);
}