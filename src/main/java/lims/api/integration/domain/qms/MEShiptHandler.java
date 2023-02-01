package lims.api.integration.domain.qms;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.enums.SAPPItemType;
import lims.api.integration.vo.QMSSendVO;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;

import java.util.List;

public class MEShiptHandler implements ShiptHandler {

    private final QMSDao qmsDao;
    private final List<QMSSendVO.ShiptBase> bases;

    private boolean allComplete = false;

    public MEShiptHandler(QMSDao qmsDao, List<QMSSendVO.ShiptBase> bases) {
        this.qmsDao = qmsDao;
        this.bases = bases;

        validate();
    }

    @Override
    public boolean isAllComplete() {
        return allComplete;
    }

    public void validate() {
        List<QMSSendVO.ShiptTest> tests;
        for (QMSSendVO.ShiptBase base : bases) {
            tests = qmsDao.findMESTestAllByOrderNoAndLotNo(base);

            if (isNoTarget(tests)) {
                return;
            }

            // 오더마감 여부 체크하기, 1개라도 오더마감이 완료되지 않은 항목이 있다면 종료
            boolean noCompleteAllTest = true;

            if (noCompleteAllTest) {
                return;
            }

            // 오더 마감이 전부 완료됐으면 QMS로 전송이 가능하도록 한다.
            allComplete = true;

        }
    }

    private boolean isNoTarget(List<QMSSendVO.ShiptTest> list) {
        return list.stream().anyMatch(el -> {
            boolean noFinishedProcess = !TestProcess.TEST_FINISH.getProcessCode().equals(el.getAnsProcCd());
            if (noFinishedProcess) {
                return true;
            }

            boolean noCreatedBySAP = !TestType.bySAP(el.getAnsTyp());
            if (noCreatedBySAP) {
                return true;
            }

            boolean notFinishedProduct = SAPPItemType.FERT != el.getMtart();
            if (notFinishedProduct) {
                return true;
            }

            return false;
        });
    }
}