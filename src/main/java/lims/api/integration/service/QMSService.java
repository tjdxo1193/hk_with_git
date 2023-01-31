package lims.api.integration.service;

import lims.api.integration.domain.qms.ShiptHandler;

public interface QMSService {

    void publishTestMethodSemiAndFinished();

    void publishShiptData(String batchNo);

    void publishShiptData(ShiptHandler handler);

    void publishOutboundTestResult();

    void publishOutOfSpecHistory();

}