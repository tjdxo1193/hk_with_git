package lims.api.integration.service;

public interface QMSService {

    void publishTestMethodSemiAndFinished();

    void publishShiptData(int orderNo, long LotNo);

    void publishOutboundTestResult();

    void publishOutOfSpecHistory();

}