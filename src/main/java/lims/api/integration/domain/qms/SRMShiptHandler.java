package lims.api.integration.domain.qms;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.vo.QMSSendVO;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class SRMShiptHandler implements ShiptHandler {

    private final QMSDao qmsDao;
    private final List<QMSSendVO.ShiptBase> bases;

    private boolean allComplete = false;

    public SRMShiptHandler(QMSDao qmsDao, List<QMSSendVO.ShiptBase> bases) {
        this.qmsDao = qmsDao;
        this.bases = bases;

        validate();
    }

    @Override
    public boolean isAllComplete() {
        return allComplete;
    }

    private void validate() {

    }

}