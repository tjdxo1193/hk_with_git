package lims.api.integration.service.impl.postProcessor.srm;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SRMDao;
import lims.api.integration.dao.SRMMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.SRMFinalOrderVO;
import lims.api.util.process.SimpleSaveProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SRMFinalOrderPostProcessor implements PostProcessor {

    private final SRMDao srmDao;
    private final SRMMasterDao masterDao;

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int count = 0;
        int degree = rev.getDegree();

        count += new SimpleSaveProcess<SRMFinalOrderVO>().forEachSave(
            srmDao.findFinalOrderByDegree(degree),
            masterDao.findFinalOrder(),
            masterDao::createFinalOrder
        ).getTotalCount();

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }
}