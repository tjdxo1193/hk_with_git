package lims.api.integration.service.impl;

import lims.api.integration.dao.InterfaceCommonDao;
import lims.api.integration.dao.MESDao;
import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.enums.TrsInterface;
import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.service.MESService;
import lims.api.integration.service.RevService;
import lims.api.integration.service.TrsService;
import lims.api.integration.service.impl.postProcessor.InterfacePostProcessorMap;
import lims.api.integration.vo.MESFinalOrderVO;
import lims.api.integration.vo.MESPackageSpecReportVO;
import lims.api.integration.vo.MESSendVO;
import lims.api.util.FileUtil;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class MESServiceImpl implements MESService {

    private final RevService revService;
    private final Publisher publisher;
    private final MESDao mesDao;
    private final InterfaceCommonDao commonDao;
    private final TrsService trsService;
    private final InterfacePostProcessorMap postProcessorMap;

    public MESServiceImpl(InterfaceSystemFactory factory,
                          HttpEntityFactory entityFactory,
                          RevService revService,
                          MESDao mesDao,
                          InterfaceCommonDao commonDao,
                          TrsService trsService,
                          InterfacePostProcessorMap postProcessorMap) {
        this.publisher = new Publisher(factory.createSystem(InterfaceSystemType.MES), entityFactory);
        this.revService = revService;
        this.mesDao = mesDao;
        this.commonDao = commonDao;
        this.trsService = trsService;
        this.postProcessorMap = postProcessorMap;
    }

    @Override
    public void savePackageSpecReport(Integer infoIdx, MESPackageSpecReportVO data, List<MultipartFile> files) {
        int count = 0;
        Integer degree = mesDao.nextDegreeInPackageSpec();
        RevInterface revInterface = RevInterface.MES_PACKAGE_SPEC_REPORT;

        if (data != null && CollectionUtils.isNotEmpty(files)) {
            for (MultipartFile file : files) {
                MESPackageSpecReportVO param = new MESPackageSpecReportVO();
                param.setIdx(mesDao.nextIdxInPackageSpec());
                param.setDegree(degree);
                param.setIfInfoIdx(infoIdx);
                param.setMatnr(data.getMatnr());
                param.setVersion(data.getVersion());
                param.setFileName(FileUtil.getName(file));
                param.setFileData(FileUtil.toBytes(file));
                count += mesDao.createPackageSpec(param);
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
    public void saveFinalOrder(Integer infoIdx, List<MESFinalOrderVO> data) {
        int count = 0;
        Integer degree = mesDao.nextDegreeInFinalOrder();
        RevInterface revInterface = RevInterface.MES_FINAL_ORDER;

        if (CollectionUtils.isNotEmpty(data)) {
            for (MESFinalOrderVO order : data) {
                order.setDegree(degree);
                order.setIdx(mesDao.nextIdxInFinalOrder());
                order.setIfInfoIdx(infoIdx);
                count += mesDao.createFinalOrder(order);
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
    public void publishTestResultJudgment(MESSendVO.TestResult data) {
        trsService.execute(
                TrsInterface.MES_TEST_RESULT,
                data,
                commonDao::nextDegreeInTestResult,
                commonDao::nextIdxInTestResult,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(MESSendVO.TestResult vo) {
                        mesDao.createTestResult(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAI(TrsInterface.MES_TEST_RESULT.getEaiServicePath(), Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(MESSendVO.TestResult vo, InterfaceTrsResponse response) {
                        commonDao.updateTrsStatusOfTestResult(vo);
                    }

                    @Override
                    public void saveOnError(MESSendVO.TestResult vo) {
                        commonDao.updateTrsStatusOfTestResult(vo);
                    }
                });
    }

    @Override
    public void publishTestStatus(MESSendVO.TestStatus data) {
        trsService.execute(
                TrsInterface.MES_TEST_STATUS,
                data,
                commonDao::nextDegreeInTestStatus,
                commonDao::nextIdxInTestStatus,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(MESSendVO.TestStatus vo) {
                        mesDao.createTestStatus(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAI(TrsInterface.MES_TEST_STATUS.getEaiServicePath(), Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(MESSendVO.TestStatus vo, InterfaceTrsResponse response) {
                        commonDao.updateTrsStatusOfTestStatus(vo);
                    }

                    @Override
                    public void saveOnError(MESSendVO.TestStatus vo) {
                        commonDao.updateTrsStatusOfTestStatus(vo);
                    }
                });
    }

    @Override
    public void publishNonCfmReport(MESSendVO.NonCfmReport data, String fileName, byte[] fileBytes) {
        trsService.execute(
                TrsInterface.MES_NON_CFM_REPORT,
                data,
                mesDao::nextDegreeInNonCfmReport,
                mesDao::nextIdxInNonCfmReport,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(MESSendVO.NonCfmReport vo) {
                        vo.setFileName(fileName);
                        vo.setFileData(fileBytes);
                        mesDao.createNonCfmReport(vo);
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.postEAIForFile(TrsInterface.MES_NON_CFM_REPORT.getEaiServicePath(), Map.of("dataList", data), fileName, fileBytes);
                    }

                    @Override
                    public void saveAfterSend(MESSendVO.NonCfmReport vo, InterfaceTrsResponse response) {
                        mesDao.updateTrsStatusOfNonCfmReport(vo);
                    }

                    @Override
                    public void saveOnError(MESSendVO.NonCfmReport vo) {
                        mesDao.updateTrsStatusOfNonCfmReport(vo);
                    }
                });
    }
}