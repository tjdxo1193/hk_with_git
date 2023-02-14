package lims.api.integration.domain.qms;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.enums.FinalOrderStatus;
import lims.api.integration.vo.QMSSendVO;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class MESShiptHandler extends ShiptAbstractHandler {

    private final QMSDao qmsDao;
    private final List<QMSSendVO.ShiptReq> data;

    private boolean ready = false;

    public MESShiptHandler(QMSDao qmsDao, List<QMSSendVO.ShiptReq> data) {
        this.qmsDao = qmsDao;
        this.data = data;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void fetch() {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        for (QMSSendVO.ShiptReq base : data) {
            List<QMSSendVO.ShiptTest> tests = qmsDao.findMESTestAllByOrderNoAndLotNo(base);
            base.setTests(tests);

            for (QMSSendVO.ShiptTest test : tests) {
                List<QMSSendVO.ShiptPerform> performs = qmsDao.findMESShiptPerformByKey(test);
                test.setPerforms(performs);
            }
        }
    }

    @Override
    public void runValidation() {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }

        for (QMSSendVO.ShiptReq base : data) {
            List<QMSSendVO.ShiptTest> tests = base.getTests();

            if (isNoTarget(tests)) {
                return;
            }

            boolean noAllCompleteTest = qmsDao.findMESFinalOrdersByOrderNoAndLotNo(base).stream()
                    .anyMatch(test -> FinalOrderStatus.FINISH.getValue() != test.getFinlStt());
            if (noAllCompleteTest) {
                return;
            }
            // 오더 마감이 전부 완료됐으면 QMS로 전송이 가능하도록 한다.
            ready = true;
        }
    }

    @Override
    public List<QMSSendVO.ShiptReq> getData() {
        return data;
    }
}