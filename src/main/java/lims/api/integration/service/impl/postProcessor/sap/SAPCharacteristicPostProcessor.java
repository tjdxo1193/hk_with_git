package lims.api.integration.service.impl.postProcessor.sap;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.dao.SAPMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.SAPCharacteristicVO;
import lims.api.util.process.SimpleSaveProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SAPCharacteristicPostProcessor implements PostProcessor {

    private final SAPDao sapDao;
    private final SAPMasterDao masterDao;

    @Override
    @PostProcessorRevExceptionHandler
    public synchronized void execute(RevStateful rev) {
        int count = 0;
        int degree = rev.getDegree();

        count += new SimpleSaveProcess<SAPCharacteristicVO.Cabn>().forEachSave(
                sapDao.findCabnAllByDegree(degree),
                masterDao.findCharacteristicCabn(),
                masterDao::createCharacteristicCabn,
                masterDao::updateCharacteristicCabn
        ).getTotalCount();

        count += new SimpleSaveProcess<SAPCharacteristicVO.Ksml>().forEachSave(
                sapDao.findKsmlAllbyDegree(degree),
                masterDao.findCharacteristicKsml(),
                masterDao::createCharacteristicKsml,
                masterDao::updateCharacteristicKsml
        ).getTotalCount();

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }
}