package lims.api.integration.domain.qms;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.vo.QMSSendVO;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MEShiptHandler implements ShiptHandler {

    private final QMSDao qmsDao;
    private final List<QMSSendVO.ShiptBase> bases;

    @Override
    public void getTestList() {
        List<QMSSendVO.ShiptBase> tests;
        for (QMSSendVO.ShiptBase base : bases) {
            tests = qmsDao.findMESTestAllByOrderNoAndLotNo(base);

            boolean existsNotFinishTest = tests.stream()
                    .anyMatch(test -> !TestProcess.TEST_FINISH.getProcessCode().equals("상태코드") || !TestType.bySAP("시험유형코드"));
            if (existsNotFinishTest) {

            }
        }
    }
}