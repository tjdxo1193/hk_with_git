package lims.api.integration.service.impl;

import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.service.QMSService;
import lims.api.integration.service.TrsService;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import org.springframework.stereotype.Service;

@Service
public class QMSServiceImpl implements QMSService {

    private final Publisher publisher;
    private final TrsService trsService;

    public QMSServiceImpl(InterfaceSystemFactory factory,
                          HttpEntityFactory entityFactory,
                          TrsService trsService) {
        this.publisher = new Publisher(factory.createSystem(InterfaceSystemType.QMS), entityFactory);
        this.trsService = trsService;
    }

    @Override
    public void publishTestMethodSemiAndFinished() {
    }

    @Override
    public void publishOutboundRequestList() {
    }

    @Override
    public void publishOutboundTestResult() {
    }

    @Override
    public void publishOutOfSpecHistory() {
    }
}