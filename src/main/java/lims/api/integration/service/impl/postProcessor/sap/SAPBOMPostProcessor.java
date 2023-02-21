package lims.api.integration.service.impl.postProcessor.sap;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.dao.SAPMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.SAPBomVO;
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
    public synchronized void execute(RevStateful rev) {
        int count = 0;

//        masterDao.deleteBOM();

        List<SAPBomVO> latestBOMs = sapDao.findBOMByDegree(rev.getDegree());
        for (SAPBomVO bom : latestBOMs) {
            count += masterDao.createBOM(bom);
        }

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }
}