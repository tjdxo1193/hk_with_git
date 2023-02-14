package lims.api.integration.service;

import lims.api.integration.domain.qms.ShiptHandler;
import lims.api.integration.vo.QMSSendVO;

import java.util.List;

public interface QMSService {

    void publishTestMethodSemiAndFinished();

    void publishShiptData(String batchNo);

    void publishShiptData(List<QMSSendVO.ShiptReq> data);

    void publishOutboundTestResult();

    void publishOutOfSpecHistory();

}