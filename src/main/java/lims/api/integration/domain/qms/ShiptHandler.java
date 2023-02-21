package lims.api.integration.domain.qms;

import lims.api.integration.enums.FinalOrderStatus;
import lims.api.integration.enums.SAPPItemType;
import lims.api.integration.enums.TestRequestType;
import lims.api.integration.vo.QMSSendVO;
import lims.api.ts.enums.TestProcess;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ShiptHandler {

    private final ShiptRepository repository;
    private final List<QMSSendVO.ShiptReq> data;

    private boolean ready = false;

    public ShiptHandler(ShiptRepository repository) {
        this.repository = repository;
        this.data = repository.getShiptRequests()
                .stream()
                .filter(req -> SAPPItemType.isFinished(req.getMtrTyp()))
                .collect(Collectors.toList());
    }

    public void fetch() {
        if (emptyData()) {
            return;
        }
        for (QMSSendVO.ShiptReq req : data) {
            List<QMSSendVO.ShiptTest> tests = repository.findTestAllByOrderNoAndLotNo(req)
                    .stream()
                    .filter(test -> TestRequestType.isShiptTarget(test.getAddCol1()))
                    .collect(Collectors.toList());
            req.setTests(tests);

            for (QMSSendVO.ShiptTest test : tests) {
                List<QMSSendVO.ShiptPerform> performs = repository.findShiptPerformByKey(test);
                test.setPerforms(performs);
            }
        }
    }

    public void runValidation() {
        if (emptyData()) {
            return;
        }
        for (QMSSendVO.ShiptReq req : data) {
            if (isNoTarget(req.getTests())) {
                return;
            }

            boolean noAllCompleteTest = repository.findFinalOrdersByOrderNoAndLotNo(req)
                    .stream()
                    .anyMatch(test -> FinalOrderStatus.FINISH.getValue() != test.getFinlStt());
            if (noAllCompleteTest) {
                return;
            }
            // 오더 마감이 전부 완료됐으면 QMS로 전송이 가능하도록 한다.
            ready = true;
        }
    }

    public boolean isReady() {
        return ready;
    }

    public List<QMSSendVO.ShiptReq> getData() {
        return data;
    }

    private boolean isTarget(List<QMSSendVO.ShiptTest> list) {
        if (emptyData()) {
            log.info("No exists data.");
            return false;
        }
        return list.stream().allMatch(el -> {
            boolean noFinishedProcess = !TestProcess.TEST_FINISH.getProcessCode().equals(el.getAnsProcCd());
            if (noFinishedProcess) {
                log.info("No send data to QMS. because there are incomplete tests. {}", el);
                return false;
            }
            return true;
        });
    }

    private boolean isNoTarget(List<QMSSendVO.ShiptTest> list) {
        return !isTarget(list);
    }

    private boolean emptyData() {
        return CollectionUtils.isEmpty(data);
    }

}