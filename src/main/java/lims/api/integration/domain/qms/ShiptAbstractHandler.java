package lims.api.integration.domain.qms;

import lims.api.integration.enums.SAPPItemType;
import lims.api.integration.vo.QMSSendVO;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@Slf4j
public abstract class ShiptAbstractHandler implements ShiptHandler {

    public abstract void runValidation();

    public abstract void fetch();

    public abstract boolean isReady();

    public abstract List<QMSSendVO.ShiptReq> getData();

    protected boolean isNoTarget(List<QMSSendVO.ShiptTest> list) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return list.stream().anyMatch(el -> {
            boolean notFinishedProduct = SAPPItemType.FERT != el.getMtart();
            if (notFinishedProduct) {
                log.info("No send data to QMS. because it is not a finished product. {}", el);
                return true;
            }
            boolean noCreatedBySAP = !TestType.bySAP(el.getAnsTyp());
            if (noCreatedBySAP) {
                log.info("No send data to QMS. because it is not a created by SAP. {}", el);
                return true;
            }
            boolean noFinishedProcess = !TestProcess.TEST_FINISH.getProcessCode().equals(el.getAnsProcCd());
            if (noFinishedProcess) {
                log.info("No send data to QMS. because there are incomplete tests. {}", el);
                return true;
            }
            return false;
        });
    }
}