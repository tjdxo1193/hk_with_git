package lims.api.integration.service.impl.postProcessor.eln;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.ELNDao;
import lims.api.integration.dao.ELNMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.ELNCtReportVO;
import lims.api.util.process.SimpleSaveProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ELNCtReportPostProcessor implements PostProcessor {

    private final ELNDao elnDao;
    private final ELNMasterDao elnMasterDao;

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int count = 0;

        Integer degree = rev.getDegree();

        count += new SimpleSaveProcess<ELNCtReportVO.File>().forEachSave(
                elnDao.findReportCtFileByDegree(degree),
                elnMasterDao.findCtReportFile(),
                elnMasterDao::createCtReportFile,
                elnMasterDao::updateCtReportFile
        ).getTotalCount();

        count += new SimpleSaveProcess<ELNCtReportVO.Matnr>().forEachSave(
                elnDao.findReportCtByDegree(degree),
                elnMasterDao.findCtReport(),
                elnMasterDao::createCtReport
        ).getTotalCount();

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }

    }
}