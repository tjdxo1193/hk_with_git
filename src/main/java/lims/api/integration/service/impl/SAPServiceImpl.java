package lims.api.integration.service.impl;

import lims.api.integration.dao.InterfaceCommonDao;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.domain.eai.*;
import lims.api.integration.enums.*;
import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.model.interfaceTrsSAPPurchasePerformResponse;
import lims.api.integration.service.QMSService;
import lims.api.integration.service.SAPService;
import lims.api.integration.service.TrsService;
import lims.api.integration.service.impl.postProcessor.InterfacePostProcessorMap;
import lims.api.integration.vo.*;
import lims.api.util.StringUtil;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import lims.api.util.process.SimpleSaveProcess;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SAPServiceImpl implements SAPService {

    private final Publisher publisher;
    private final SAPDao sapDao;
    private final InterfaceCommonDao commonDao;
    private final TrsService trsService;
    private final InterfacePostProcessorMap postProcessorMap;
    private final QMSService qmsService;

    public SAPServiceImpl(InterfaceSystemFactory factory,
                          HttpEntityFactory entityFactory,
                          SAPDao sapDao,
                          InterfaceCommonDao commonDao,
                          TrsService trsService,
                          InterfacePostProcessorMap postProcessorMap,
                          QMSService qmsService) {
        this.publisher = new Publisher(factory.createSystem(InterfaceSystemType.SAP), entityFactory);
        this.sapDao = sapDao;
        this.commonDao = commonDao;
        this.trsService = trsService;
        this.postProcessorMap = postProcessorMap;
        this.qmsService = qmsService;
    }

    @Override
    public void saveBOM(Integer infoIdx, List<SAPBomVO> param, String parentGuid) {
        int count = 0;
        Integer degree = sapDao.nextDegreeInBOM();
        RevInterface revInterface = RevInterface.SAP_BOM;

        if (CollectionUtils.isNotEmpty(param)) {
            for (SAPBomVO vo : param) {
                vo.setDegree(degree);
                vo.setParentGuid(parentGuid);
                vo.setIfInfoIdx(infoIdx);
                count += sapDao.createBOM(vo);
            }
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
    public void saveMaterial(Integer infoIdx, SAPMaterialVO param) {
        int count = 0;
        Integer degree = sapDao.nextDegreeInMaterial();
        RevInterface revInterface = RevInterface.SAP_MATERIAL;

        if (CollectionUtils.isNotEmpty(param.getMara())) {
            for (SAPMaterialVO.Mara mara : param.getMara()) {
                mara.setDegree(degree);
                mara.setIfInfoIdx(infoIdx);
                count += sapDao.createMaterialMara(mara);
            }
        }

        if (CollectionUtils.isNotEmpty(param.getMarc())) {
            for (SAPMaterialVO.Marc marc : param.getMarc()) {
                marc.setDegree(degree);
                marc.setIfInfoIdx(infoIdx);
                count += sapDao.createMaterialMarc(marc);
            }
        }

        if (CollectionUtils.isNotEmpty(param.getMvke())) {
            for (SAPMaterialVO.Mvke mvke : param.getMvke()) {
                mvke.setDegree(degree);
                mvke.setIfInfoIdx(infoIdx);
                count += sapDao.createMaterialMvke(mvke);
            }
        }

        if (CollectionUtils.isNotEmpty(param.getZmdv())) {
            for (SAPMaterialVO.Zmdv zmdv : param.getZmdv()) {
                zmdv.setDegree(degree);
                zmdv.setIfInfoIdx(infoIdx);
                count += sapDao.createMaterialZmdv(zmdv);
            }
        }

        if (CollectionUtils.isNotEmpty(param.getMakt())) {
            for (SAPMaterialVO.Makt makt : param.getMakt()) {
                makt.setDegree(degree);
                makt.setIfInfoIdx(infoIdx);
                count += sapDao.createMaterialMakt(makt);
            }
        }

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }

//        new Thread(() -> qmsService
//                .publishMaterial(param))
//                .start();
        new Thread(() -> postProcessorMap
                .get(revInterface)
                .execute(new RevStateful(degree, infoIdx)))
                .start();
    }

    @Override
    public synchronized void saveCharacteristic(Integer infoIdx, SAPCharacteristicVO data) {
        int count = 0;
        Integer degree = sapDao.nextDegreeInCharacteristic();
        RevInterface revInterface = RevInterface.SAP_CHARACTERISTIC;

        if (CollectionUtils.isNotEmpty(data.getCabn())) {
            for (SAPCharacteristicVO.Cabn cabn : data.getCabn()) {
                cabn.setDegree(degree);
                cabn.setIdx(sapDao.nextIdxInCharacteristicCabn());
                cabn.setIfInfoIdx(infoIdx);
                count += sapDao.createCharacteristicCabn(cabn);
            }
        }

        if (CollectionUtils.isNotEmpty(data.getKsml())) {
            for (SAPCharacteristicVO.Ksml ksml : data.getKsml()) {
                ksml.setDegree(degree);
                ksml.setIdx(sapDao.nextIdxInCharacteristicKsml());
                ksml.setIfInfoIdx(infoIdx);
                count += sapDao.createCharacteristicKsml(ksml);
            }
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
    public synchronized void saveCalendar(Integer infoIdx, List<SAPCalendarVO> param) {
        int count = 0;
        Integer degree = sapDao.nextDegreeInCalendar();
        RevInterface revInterface = RevInterface.SAP_CALENDAR;

        for (SAPCalendarVO vo : param) {
            vo.setDegree(degree);
            vo.setIdx(sapDao.nextIdxInCalendar());
            vo.setIfInfoIdx(infoIdx);
            count += sapDao.createCalendar(vo);
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
    public synchronized void testRequest(Integer infoIdx, SAPTestRequestVO param) {
        int count = 0;
        Integer degree = sapDao.nextDegreeInTestRequest();
        RevInterface revInterface = RevInterface.SAP_TEST_REQUEST;

        SAPTestRequestVO.RequestHeader header = param.getRequestHeader();
        if (header != null) {
            header.setDegree(degree);
            header.setIdx(sapDao.nextIdxInTestRequestHeaders());
            header.setIfInfoIdx(infoIdx);
            count += sapDao.createTestRequestHeader(header);
        }

        List<SAPTestRequestVO.RequestDetails> details = param.getRequestDetails();
        if (CollectionUtils.isNotEmpty(details)) {
            for (SAPTestRequestVO.RequestDetails detail : details) {
                detail.setDegree(degree);
                detail.setIdx(sapDao.nextIdxInTestRequestDetails());
                detail.setIfInfoIdx(infoIdx);
                count += sapDao.createTestRequestDetails(detail);
            }
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
    public synchronized void saveInputPerformanceByBatch(Integer infoIdx, SAPInputPerformanceByBatchVO param) {
        int count = 0;
        Integer degree = sapDao.nextDegreeInInputPerformance();
        RevInterface revInterface = RevInterface.SAP_INPUT_PERFORMANCE;

        List<SAPInputPerformanceByBatchVO.InputPerformanceHeader> headers = param.getPerformanceHeader();
        if (CollectionUtils.isNotEmpty(headers)) {
            for (SAPInputPerformanceByBatchVO.InputPerformanceHeader header : headers) {
                header.setDegree(degree);
                header.setIdx(sapDao.nextIdxInInputPerformanceHeader());
                header.setIfInfoIdx(infoIdx);
                count += sapDao.createInputPerformanceHeader(header);
            }
        }

        List<SAPInputPerformanceByBatchVO.InputPerformanceDetail> details = param.getPerformanceDetails();
        if (CollectionUtils.isNotEmpty(details)) {
            for (SAPInputPerformanceByBatchVO.InputPerformanceDetail detail : details) {
                detail.setDegree(degree);
                detail.setIdx(sapDao.nextIdxInInputPerformanceDetail());
                detail.setIfInfoIdx(infoIdx);
                count += sapDao.createInputPerformanceDetail(detail);
            }
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
    public void saveBusinessPartner(Integer infoIdx, List<SAPBusinessPartnerVO> data) {
        int count = 0;

        if (CollectionUtils.isNotEmpty(data)) {
            count = new SimpleSaveProcess<SAPBusinessPartnerVO>().forEachSave(
                    data,
                    sapDao.findBusinessPartner(),
                    partner -> {
                        partner.setIfInfoIdx(infoIdx);
                        return sapDao.createBusinessPartner(partner);
                    },
                    partner -> {
                        partner.setIfInfoIdx(infoIdx);
                        return sapDao.updateBusinessPartner(partner);
                    }
            ).getTotalCount();
        }

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }

    @Override
    public void publishTestResultJudgment(SAPSendVO.TestResult data) {
        trsService.execute(
                TrsInterface.SAP_TEST_RESULT,
                data,
                commonDao::nextDegreeInTestResult,
                commonDao::nextIdxInTestResult,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(SAPSendVO.TestResult vo) {
                        sapDao.createTestResult(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAI(TrsInterface.SAP_TEST_RESULT.getServicePath(), Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(SAPSendVO.TestResult vo, InterfaceTrsResponse response) {
                        commonDao.updateTrsStatusOfTestResult(vo);
                    }

                    @Override
                    public void saveOnError(SAPSendVO.TestResult vo) {
                        commonDao.updateTrsStatusOfTestResult(vo);
                    }
                });
    }

    @Override
    public void publishTestStatus(SAPSendVO.TestStatus data) {
        trsService.execute(
                TrsInterface.SAP_TEST_STATUS,
                data,
                commonDao::nextDegreeInTestStatus,
                commonDao::nextIdxInTestStatus,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(SAPSendVO.TestStatus vo) {
                        sapDao.createTestStatus(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAI(TrsInterface.SAP_TEST_STATUS.getServicePath(), Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(SAPSendVO.TestStatus vo, InterfaceTrsResponse response) {
                        commonDao.updateTrsStatusOfTestStatus(vo);
                    }

                    @Override
                    public void saveOnError(SAPSendVO.TestStatus vo) {
                        commonDao.updateTrsStatusOfTestStatus(vo);
                    }
                });
    }

    @Override
    public TrsResult publishTestPerformanceOfPurchaseInbound(SAPSendVO.TestPerformanceOfPurchaseInbound data) {
        return trsService.execute(
                TrsInterface.SAP_TEST_PERFORM_OF_INBOUND_PURCHASE,
                data,
                sapDao::nextDegreeInTestPerformanceOfPurchaseInbound,
                sapDao::nextIdxInTestPerformanceOfPurchaseInbound, new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(SAPSendVO.TestPerformanceOfPurchaseInbound vo) {
                        sapDao.createTestPerformanceOfPurchaseInbound(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        interfaceTrsSAPPurchasePerformResponse response = publisher.postEAI(
                                TrsInterface.SAP_TEST_PERFORM_OF_INBOUND_PURCHASE.getServicePath(),
                                Map.of("dataList", data),
                                interfaceTrsSAPPurchasePerformResponse.class);
                        return response.toTrsResponse();
                    }

                    @Override
                    public void saveAfterSend(SAPSendVO.TestPerformanceOfPurchaseInbound vo, InterfaceTrsResponse response) {
                        ResponseMultiData responseMultiData = response.getDataList();
                        /**
                         * 에러 정보 응답 목록이 있으면 에러 정보 중 임의 하나를 세팅
                         *
                         * 어떤 에러가 발생해서 실패했다면 해당 에러가 원인일 것이므로 목록이 와도 원인 에러가 전부 동일할 것이라고 판단.
                         * 따라서 에러 목록 중 임의 에러 하나만 저장함.
                         */
                        if (CollectionUtils.isNotEmpty(responseMultiData)) {
                            Map<String, String> responseData = responseMultiData.stream()
                                    .filter(map -> vo.getWerks().equals(map.get("werks")) && vo.getMatnr().equals(map.get("matnr")))
                                    .findAny().orElse(new HashMap<>());
                            vo.setResponseStatus(InterfaceResponseStatus.of(responseData.get("xstat")));
                            vo.setResponseMessage(responseData.get("xmsg"));
                        }
                        /**
                         * 저장된 응답 상태 및 메시지가 없으면 기본 상태 코드 및 메시지 세팅
                         */
                        if (vo.getResponseStatus() == null) {
                            vo.setResponseStatus(response.getXstat());
                            vo.setResponseMessage(vo.getResponseStatus().getMessage());
                        }
                        sapDao.updateTrsStatusOfTestPerformanceOfPurchaseInbound(vo);
                    }

                    @Override
                    public void saveOnError(SAPSendVO.TestPerformanceOfPurchaseInbound vo) {
                        sapDao.updateTrsStatusOfTestPerformanceOfPurchaseInbound(vo);
                    }
                });
    }

    @Override
    public TrsResult publishTestPerformanceOfManufactureInbound(SAPSendVO.TestPerformanceOfManufactureInbound data) {
        data.setGuid(StringUtil.generateUUID(22));
        data.setSeq(1);

        return trsService.execute(
                TrsInterface.SAP_TEST_PERFORM_OF_INBOUND_MANUFACTURE,
                data,
                sapDao::nextDegreeInTestPerformanceOfManufactureInbound,
                sapDao::nextIdxInTestPerformanceOfManufactureInbound,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(SAPSendVO.TestPerformanceOfManufactureInbound vo) {
                        sapDao.createTestPerformanceOfManufactureInbound(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAI(
                                TrsInterface.SAP_TEST_PERFORM_OF_INBOUND_MANUFACTURE.getServicePath(),
                                Map.of("dataList", data)
                        );
                    }

                    @Override
                    public void saveAfterSend(SAPSendVO.TestPerformanceOfManufactureInbound vo, InterfaceTrsResponse response) {
                        sapDao.updateTrsStatusOfTestPerformanceOfManufactureInbound(vo);
                    }

                    @Override
                    public void saveOnError(SAPSendVO.TestPerformanceOfManufactureInbound vo) {
                        sapDao.updateTrsStatusOfTestPerformanceOfManufactureInbound(vo);
                    }
                });
    }

    @Override
    public void publishAssetsMovementHistory(List<SAPSendVO.AssetsMovementHistory> data) {
        trsService.execute(
                TrsInterface.SAP_ASSETS_MOVEMENT_HISTORY,
                data,
                sapDao::nextDegreeInAssetsMovementHistory,
                sapDao::nextIdxInAssetsMovementHistory,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(SAPSendVO.AssetsMovementHistory vo) {
                        sapDao.createAssetsMovementHistory(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        Map<String, Object> body = new HashMap<>();
                        body.put("bukrs", CompanyType.KOLMAR.getCode());
                        body.put("xsysid", "LIMS");
                        body.put("dataList", data);
                        return publisher.postEAI(
                                TrsInterface.SAP_ASSETS_MOVEMENT_HISTORY.getServicePath(),
                                body
                        );
                    }

                    @Override
                    public void saveAfterSend(SAPSendVO.AssetsMovementHistory vo, InterfaceTrsResponse response) {
                        sapDao.updateTrsStatusOfAssetsMovementHistory(vo);
                    }

                    @Override
                    public void saveOnError(SAPSendVO.AssetsMovementHistory vo) {
                        sapDao.updateTrsStatusOfAssetsMovementHistory(vo);
                    }
                });
    }
}