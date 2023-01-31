package lims.api.integration.service.impl;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.domain.qms.MEShiptHandler;
import lims.api.integration.domain.qms.SRMShiptHandler;
import lims.api.integration.domain.qms.ShiptHandler;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.service.QMSService;
import lims.api.integration.service.TrsService;
import lims.api.integration.vo.QMSSendVO;
import lims.api.ts.enums.TestType;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QMSServiceImpl implements QMSService {

    private final Publisher publisher;
    private final TrsService trsService;
    private final QMSDao qmsDao;

    public QMSServiceImpl(InterfaceSystemFactory factory,
                          HttpEntityFactory entityFactory,
                          TrsService trsService,
                          QMSDao qmsDao) {
        this.publisher = new Publisher(factory.createSystem(InterfaceSystemType.QMS), entityFactory);
        this.trsService = trsService;
        this.qmsDao = qmsDao;
    }

    @Override
    public void publishTestMethodSemiAndFinished() {
    }

    @Override
    public void publishShiptData(String batchNo) {
        List<QMSSendVO.ShiptBase> data = qmsDao.findShiptBaseByBatchNo(batchNo);
//                .stream()
//                .filter(base -> TestType.bySAP(base.getAnsTyp()))
//                .collect(Collectors.toList());

        List<QMSSendVO.ShiptBase> dataByMES = data.stream()
                .filter(base -> StringUtils.isNotEmpty(base.getPdtOrderNo()))
                .collect(Collectors.toList());

        List<QMSSendVO.ShiptBase> dataBySRM = data.stream()
                .filter(base -> StringUtils.isNotEmpty(base.getPhsOrderNo()))
                .collect(Collectors.toList());

        publishShiptData(new MEShiptHandler(qmsDao, dataByMES));
        publishShiptData(new SRMShiptHandler(qmsDao, dataBySRM));
    }

    @Override
    public void publishShiptData(ShiptHandler handler) {

    }

    @Override
    public void publishOutboundTestResult() {
    }

    @Override
    public void publishOutOfSpecHistory() {
    }

    private void doPublish() {
//        HttpRestInfo<T> info = createEAIHttpInfo(path, responseType);
//        HttpEntity<String> entity = entityFactory.json(bodyParameter);
//        return template.request(new HttpPostExecutor<>(info, entity));
    }

}