package lims.api.integration.service;

public interface QMSService {

    void publishTestMethodSemiAndFinished();

    void publishOutboundRequestList();

    void publishOutboundTestResult();

    void publishOutOfSpecHistory();

}