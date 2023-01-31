package lims.api.integration.domain.qms;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.vo.QMSSendVO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SRMShiptHandler implements ShiptHandler {

    private final QMSDao qmsDao;
    private final List<QMSSendVO.ShiptBase> bases;

    @Override
    public void getTestList() {
    }
}