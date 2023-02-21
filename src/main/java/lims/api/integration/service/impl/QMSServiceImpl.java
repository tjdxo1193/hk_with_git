package lims.api.integration.service.impl;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.domain.qms.MESShiptRepository;
import lims.api.integration.domain.qms.SRMShiptRepository;
import lims.api.integration.domain.qms.ShiptHandler;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.TrsInterface;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.service.QMSService;
import lims.api.integration.service.TrsService;
import lims.api.integration.vo.QMSSendVO;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public void publishShiptData(String batchNo) {
        ShiptHandler mesHandler = new ShiptHandler(new MESShiptRepository(qmsDao, batchNo));
        ShiptHandler srmHandler = new ShiptHandler(new SRMShiptRepository(qmsDao, batchNo));
        publishShiptDataByHandler(mesHandler);
        publishShiptDataByHandler(srmHandler);
    }

    private void publishShiptDataByHandler(ShiptHandler handler) {
        handler.fetch();
        handler.runValidation();
        if (handler.isReady()) {
            publishShiptData(handler.getData());
        }
    }

    @Override
    public void publishShiptData(List<QMSSendVO.ShiptReq> data) {
        trsService.execute(
                TrsInterface.QMS_SHIPT,
                data,
                qmsDao::nextDegreeInShipt,
                qmsDao::nextIdxInShipt,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(QMSSendVO.ShiptReq vo) {
                        qmsDao.createShipt(vo);

                        for (QMSSendVO.ShiptTest test : vo.getTests()) {
                            test.setShiptIdx(vo.getIdx());
                            qmsDao.createShiptTest(test);

                            for (QMSSendVO.ShiptPerform perform : test.getPerforms()) {
                                perform.setShiptTestIdx(test.getShiptIdx());
                                qmsDao.createShiptPerform(perform);
                            }
                        }
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.post("/interface/lims/shipt.qms", InterfaceTrsResponse.class, Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(QMSSendVO.ShiptReq vo, InterfaceTrsResponse response) {
                        qmsDao.updateShipt(vo);
                    }

                    @Override
                    public void saveOnError(QMSSendVO.ShiptReq vo) {
                        qmsDao.updateShipt(vo);
                    }
                }
        );
    }

}