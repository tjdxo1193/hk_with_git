package lims.api.integration.service.impl.postProcessor.sap;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.dao.SAPMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.SAPBomVO;
import lims.api.util.process.SimpleSaveProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SAPBOMPostProcessor implements PostProcessor {

    private final SAPDao sapDao;
    private final SAPMasterDao masterDao;

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int count = 0;

        List<SAPBomVO> latestBOMs = sapDao.findBOMByDegree(rev.getDegree());

        count += new SimpleSaveProcess<SAPBomVO>().forEachSave(
                latestBOMs,
                masterDao.findBOM(),
                masterDao::createBOM,
                masterDao::updateBOM
        ).getTotalCount();

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }
}