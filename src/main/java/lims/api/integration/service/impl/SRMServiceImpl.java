package lims.api.integration.service.impl;

import lims.api.integration.dao.InterfaceCommonDao;
import lims.api.integration.dao.SRMDao;
import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.enums.TrsInterface;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.service.RevService;
import lims.api.integration.service.SRMService;
import lims.api.integration.service.TrsService;
import lims.api.integration.service.impl.postProcessor.InterfacePostProcessorMap;
import lims.api.integration.vo.SRMFinalOrderVO;
import lims.api.integration.vo.SRMReoccurPreventReportVO;
import lims.api.integration.vo.SRMSendVO;
import lims.api.integration.vo.SRMSupplierReportVO;
import lims.api.util.FileUtil;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Qualifier
@Service
public class SRMServiceImpl implements SRMService {

    private final RevService revService;
    private final Publisher publisher;
    private final SRMDao srmDao;
    private final InterfaceCommonDao commonDao;
    private final TrsService trsService;
    private final InterfacePostProcessorMap postProcessorMap;

    public SRMServiceImpl(InterfaceSystemFactory factory,
                          HttpEntityFactory entityFactory,
                          RevService revService,
                          SRMDao srmDao,
                          InterfaceCommonDao commonDao,
                          TrsService trsService,
                          InterfacePostProcessorMap postProcessorMap) {
        this.publisher = new Publisher(factory.createSystem(InterfaceSystemType.SRM), entityFactory);
        this.revService = revService;
        this.srmDao = srmDao;
        this.commonDao = commonDao;
        this.trsService = trsService;
        this.postProcessorMap = postProcessorMap;
    }

    @Override
    public void saveReoccurPreventReport(Integer infoIdx, SRMReoccurPreventReportVO data, List<MultipartFile> files) {
        RevInterface revInterface = RevInterface.SRM_REOCCUR_PREVENT_REPORT;

        revService.execute(
                revInterface,
                srmDao.nextDegreeInReoccurPreventionReport(),
                degree -> {
                    int count = 0;

                    if (data != null && CollectionUtils.isNotEmpty(files)) {
                        for (MultipartFile file : files) {
                            SRMReoccurPreventReportVO param = new SRMReoccurPreventReportVO();
                            param.setIdx(srmDao.nextIdxInReoccurPreventionReport());
                            param.setDegree(degree);
                            param.setIfInfoIdx(infoIdx);
                            param.setIfId(data.getIfId());
                            param.setPhsOrderNo(data.getPhsOrderNo());
                            param.setLotNo(data.getLotNo());
                            param.setBatchNo(data.getBatchNo());
                            param.setFileId(data.getFileId());
                            param.setFileName(FileUtil.getName(file));
                            param.setFileData(FileUtil.toBytes(file));
                            count += srmDao.createReoccurPreventionReport(param);
                        }
                    }
                    return count;
                },
                degree -> new Thread(() -> postProcessorMap
                        .get(revInterface)
                        .execute(new RevStateful(degree, infoIdx)))
                        .start()
        );
    }

    @Override
    public void saveSupplierReport(Integer infoIdx, SRMSupplierReportVO data, List<MultipartFile> files) {
        RevInterface revInterface = RevInterface.SRM_CONSIGNMENT_AND_SUPPLIER_REPORT;

        revService.execute(
                revInterface,
                srmDao.nextDegreeInConsignSupplierReport(),
                degree -> {
                    int count = 0;

                    if (data != null && CollectionUtils.isNotEmpty(files)) {
                        for (MultipartFile file : files) {
                            SRMSupplierReportVO param = new SRMSupplierReportVO();
                            param.setIdx(srmDao.nextIdxInConsignSupplierReport());
                            param.setDegree(degree);
                            param.setIfInfoIdx(infoIdx);
                            param.setIfId(data.getIfId());
                            param.setPhsOrderNo(data.getPhsOrderNo());
                            param.setLotNo(data.getLotNo());
                            param.setBatchNo(data.getBatchNo());
                            param.setReportDiv(data.getReportDiv());
                            param.setFileId(data.getFileId());
                            param.setFileName(FileUtil.getName(file));
                            param.setFileData(FileUtil.toBytes(file));
                            count += srmDao.createConsignSupplierReport(param);
                        }
                    }
                    return count;
                },
                degree -> new Thread(() -> postProcessorMap
                        .get(revInterface)
                        .execute(new RevStateful(degree, infoIdx)))
                        .start()
        );
    }

    @Override
    public void saveFinalOrder(Integer infoIdx, List<SRMFinalOrderVO> data) {
        RevInterface revInterface = RevInterface.SRM_FINAL_ORDER;

        revService.execute(
                revInterface,
                srmDao.nextDegreeInFinalOrder(),
                degree -> {
                    int count = 0;

                    if (CollectionUtils.isNotEmpty(data)) {
                        for (SRMFinalOrderVO vo : data) {
                            vo.setDegree(degree);
                            vo.setIdx(srmDao.nextIdxInFinalOrder());
                            vo.setIfInfoIdx(infoIdx);
                            count += srmDao.createFinalOrder(vo);
                        }
                    }
                    return count;
                },
                degree -> new Thread(() -> postProcessorMap
                        .get(revInterface)
                        .execute(new RevStateful(degree, infoIdx)))
                        .start()
        );
    }

    @Override
    public void publishTestResultJudgment(SRMSendVO.TestResult data) {
        trsService.execute(
                TrsInterface.SRM_TEST_RESULT,
                data,
                commonDao::nextDegreeInTestResult,
                commonDao::nextIdxInTestResult,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(SRMSendVO.TestResult vo) {
                        srmDao.createTestResult(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAI(TrsInterface.SRM_TEST_RESULT.getEaiServicePath(), Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(SRMSendVO.TestResult vo, InterfaceTrsResponse response) {
                        commonDao.updateTrsStatusOfTestResult(vo);
                    }

                    @Override
                    public void saveOnError(SRMSendVO.TestResult vo) {
                        commonDao.updateTrsStatusOfTestResult(vo);
                    }
                });
    }

    @Override
    public void publishTestStatus(SRMSendVO.TestStatus data) {
        trsService.execute(
                TrsInterface.SRM_TEST_STATUS,
                data,
                commonDao::nextDegreeInTestStatus,
                commonDao::nextIdxInTestStatus,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(SRMSendVO.TestStatus vo) {
                        srmDao.createTestStatus(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAI(TrsInterface.SRM_TEST_STATUS.getEaiServicePath(), Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(SRMSendVO.TestStatus vo, InterfaceTrsResponse response) {
                        commonDao.updateTrsStatusOfTestStatus(vo);
                    }

                    @Override
                    public void saveOnError(SRMSendVO.TestStatus vo) {
                        commonDao.updateTrsStatusOfTestStatus(vo);
                    }
                });
    }

    @Override
    public void publishNonCfmReport(SRMSendVO.NonCfmReport data, String fileName, byte[] fileBytes) {
        trsService.execute(
                TrsInterface.SRM_NON_CFM_REPORT,
                data,
                srmDao::nextDegreeInNonCfmReport,
                srmDao::nextIdxInNonCfmReport,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(SRMSendVO.NonCfmReport vo) {
                        vo.setFileName(fileName);
                        vo.setFileData(fileBytes);
                        srmDao.createNonCfmReport(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAIForFile(TrsInterface.SRM_NON_CFM_REPORT.getEaiServicePath(), Map.of("dataList", data), fileName, fileBytes);
                    }

                    @Override
                    public void saveAfterSend(SRMSendVO.NonCfmReport vo, InterfaceTrsResponse response) {
                        srmDao.updateTrsStatusOfNonCfmReport(vo);
                    }

                    @Override
                    public void saveOnError(SRMSendVO.NonCfmReport vo) {
                        srmDao.updateTrsStatusOfNonCfmReport(vo);
                    }
                });
    }
}