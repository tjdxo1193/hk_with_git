package lims.api.integration.service.impl.postProcessor.mes;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.MESDao;
import lims.api.integration.dao.MESMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.MESPackageSpecReportVO;
import lims.api.util.process.SimpleSaveProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MESPackageSpecReportPostProcessor implements PostProcessor {

    private final MESDao mesDao;
    private final MESMasterDao masterDao;

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int count = 0;
        int degree = rev.getDegree();

        List<MESPackageSpecReportVO> interfaceData = mesDao.findPackageSpecByDegree(degree);

        count += new SimpleSaveProcess<MESPackageSpecReportVO>().forEachSave(
                interfaceData,
                masterDao.findPackageSpec(),
                masterDao::createPackageSpec,
                masterDao::updatePackageSpec
        ).getTotalCount();

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }
}