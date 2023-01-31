package lims.api.integration.service.impl;

import lims.api.integration.dao.ELNDao;
import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.enums.ELNCmdType;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.enums.TrsInterface;
import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.service.ELNService;
import lims.api.integration.service.TrsService;
import lims.api.integration.service.impl.postProcessor.InterfacePostProcessorMap;
import lims.api.integration.vo.ELNCtReportVO;
import lims.api.integration.vo.ELNSendVO;
import lims.api.integration.vo.ELNStandardSpecVO;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ELNServiceImpl implements ELNService {

    private final Publisher publisher;
    private final ELNDao elnDao;
    private final TrsService trsService;
    private final InterfacePostProcessorMap postProcessorMap;

    public ELNServiceImpl(InterfaceSystemFactory factory,
                          HttpEntityFactory entityFactory,
                          ELNDao elnDao,
                          TrsService trsService,
                          InterfacePostProcessorMap postProcessorMap) {
        this.publisher = new Publisher(factory.createSystem(InterfaceSystemType.ELN), entityFactory);
        this.elnDao = elnDao;
        this.trsService = trsService;
        this.postProcessorMap = postProcessorMap;
    }

    @Override
    public void saveCtReport(Integer infoIdx, ELNCtReportVO param) {
        int count = 0;
        Integer degree = elnDao.nextDegreeInReportCt();
        RevInterface revInterface = RevInterface.ELN_CT_REPORT;

        ELNCtReportVO.File file = param.getFile();
        file.setDegree(degree);
        file.setIdx(elnDao.nextIdxInReportCtFile());
        file.decodedFileData();
        file.setIfInfoIdx(infoIdx);
        count += elnDao.createReportCtFile(file);

        for (ELNCtReportVO.Matnr matnr : param.getMatnr()) {
            matnr.setDegree(degree);
            matnr.setIdx(elnDao.nextIdxInReportCtMatnr());
            matnr.setIfInfoIdx(infoIdx);
            count += elnDao.createReportCtMatnr(matnr);
        }

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }

        new Thread(() -> postProcessorMap
                .get(revInterface)
                .execute(new RevStateful(degree, infoIdx)))
                .start();
    }

    @Override
    public void saveFinishedAndSemiStandard(Integer infoIdx, List<ELNStandardSpecVO> data) {
        int count = 0;
        Integer degree = elnDao.nextDegreeInStandardSpec();
        RevInterface revInterface = RevInterface.ELN_STANDARD_FINISH_AND_SEMI;

        for (ELNStandardSpecVO standard : data) {
            standard.setDegree(degree);
            standard.setIdx(elnDao.nextIdxInStandardSpec());
            standard.setIfInfoIdx(infoIdx);
            count += elnDao.createStandardSpec(standard);
        }

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }

        new Thread(() -> postProcessorMap
                .get(revInterface)
                .execute(new RevStateful(degree, infoIdx)))
                .start();
    }

    @Override
    public void publishTestMethodByItem(ELNCmdType cmdType, List<ELNSendVO.TestMethodByItem> ids) {
        String idsForInClause = ids.stream().map(ELNSendVO.TestMethodByItem::getAmitmCd).filter(Objects::nonNull).collect(Collectors.joining("', '"));
        List<ELNSendVO.TestMethodByItem> data = elnDao.findTestMethodByItemIds("'" + idsForInClause + "'");
        data.forEach(item -> item.setCmdType(cmdType));

        trsService.executeAsync(
                TrsInterface.ELN_METHOD_BY_ITEM,
                data,
                elnDao::nextDegreeInTestMethodByItem,
                elnDao::nextIdxInTestMethodByItem,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(ELNSendVO.TestMethodByItem vo) {
                        elnDao.createTestMethodByItem(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAI(TrsInterface.ELN_METHOD_BY_ITEM.getEaiServicePath(), Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(ELNSendVO.TestMethodByItem vo, InterfaceTrsResponse response) {
                        elnDao.updateTrsStatusOfTestMethodByItem(vo);
                    }

                    @Override
                    public void saveOnError(ELNSendVO.TestMethodByItem vo) {
                        elnDao.updateTrsStatusOfTestMethodByItem(vo);
                    }
                });
    }
}