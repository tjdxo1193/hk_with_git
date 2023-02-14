package lims.api.integration.service.impl;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.domain.qms.MESShiptHandler;
import lims.api.integration.domain.qms.SRMShiptHandler;
import lims.api.integration.domain.qms.ShiptHandler;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.TrsInterface;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.service.QMSService;
import lims.api.integration.service.TrsService;
import lims.api.integration.vo.QMSSendVO;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        List<QMSSendVO.ShiptReq> data = qmsDao.findShiptReqByBatchNo(batchNo);

        List<QMSSendVO.ShiptReq> dataByMES = data.stream()
                .filter(base -> StringUtils.isNotEmpty(base.getPdtOrderNo()))
                .collect(Collectors.toList());

        List<QMSSendVO.ShiptReq> dataBySRM = data.stream()
                .filter(base -> StringUtils.isNotEmpty(base.getPhsOrderNo()))
                .collect(Collectors.toList());

        ShiptHandler mesShiptHandler = new MESShiptHandler(qmsDao, dataByMES);
        ShiptHandler srmShiptHandler = new SRMShiptHandler(qmsDao, dataBySRM);

        mesShiptHandler.fetch();
        srmShiptHandler.fetch();

        mesShiptHandler.runValidation();
        srmShiptHandler.runValidation();

        if (mesShiptHandler.isReady()) {
            publishShiptData(mesShiptHandler.getData());
        }
        if (srmShiptHandler.isReady()) {
            publishShiptData(srmShiptHandler.getData());
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

    @Override
    public void publishOutboundTestResult() {
    }

    @Override
    public void publishOutOfSpecHistory() {
    }

}