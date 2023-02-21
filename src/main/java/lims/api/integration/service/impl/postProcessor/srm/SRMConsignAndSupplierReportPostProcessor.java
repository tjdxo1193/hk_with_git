package lims.api.integration.service.impl.postProcessor.srm;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SRMDao;
import lims.api.integration.dao.SRMMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.SRMSupplierReportVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SRMConsignAndSupplierReportPostProcessor implements PostProcessor {

    private final SRMDao srmDao;
    private final SRMMasterDao masterDao;

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int count = 0;
        int degree = rev.getDegree();

        List<SRMSupplierReportVO> interfaceData = srmDao.findConsignSupplierReportByDegree(degree);

        if (CollectionUtils.isNotEmpty(interfaceData)) {
            for (SRMSupplierReportVO data : interfaceData) {
                int seq = masterDao.nextSeqInConsignSupplierReport(data.getBatchNo());
                data.setSeq(seq);
                count += masterDao.createConsignSupplierReport(data);
            }
        }

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }
}