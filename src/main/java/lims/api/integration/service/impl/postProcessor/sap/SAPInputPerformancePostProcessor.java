package lims.api.integration.service.impl.postProcessor.sap;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.dao.SAPMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.SAPInputPerformanceByBatchVO;
import lims.api.util.process.KeyObject;
import lims.api.util.process.SimpleSaveProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SAPInputPerformancePostProcessor implements PostProcessor {

    private final SAPDao sapDao;
    private final SAPMasterDao masterDao;

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int count = 0;
        int degree = rev.getDegree();

        List<SAPInputPerformanceByBatchVO.InputPerformanceHeader> interfaceHeaderData = sapDao.findInputPerformHeaderAllByDegree(degree);

        count += new SimpleSaveProcess<SAPInputPerformanceByBatchVO.InputPerformanceHeader>().forEachSave(
                interfaceHeaderData,
                masterDao.findInputPerformHeader(),
                masterDao::createInputPerformHeader,
                masterDao::updateInputPerformHeader
        ).getTotalCount();

        List<SAPInputPerformanceByBatchVO.InputPerformanceDetail> interfaceDetailData = getDetailListWithLotNo(degree, interfaceHeaderData);
        count += new SimpleSaveProcess<SAPInputPerformanceByBatchVO.InputPerformanceDetail>().forEachSave(
                interfaceDetailData,
                masterDao.findInputPerformDetail(),
                masterDao::createInputPerformDetail,
                masterDao::updateInputPerformDetail
        ).getTotalCount();

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }

    private List<SAPInputPerformanceByBatchVO.InputPerformanceDetail> getDetailListWithLotNo(int degree, List<SAPInputPerformanceByBatchVO.InputPerformanceHeader> interfaceHeaderData) {
        Map<KeyObject, SAPInputPerformanceByBatchVO.InputPerformanceHeader> headerMap = interfaceHeaderData.stream()
                .collect(Collectors.toMap(SAPInputPerformanceByBatchVO.InputPerformanceHeader::generateKey, header -> header, (oldValue, newValue) -> oldValue));

        return sapDao.findInputPerformDetailAllByDegree(degree).stream().map(detail -> {
            KeyObject key = new KeyObject();
            key.put("werks", detail.getWerks());
            key.put("matnr", detail.getMatnr());
            key.put("charg", detail.getCharg());

            SAPInputPerformanceByBatchVO.InputPerformanceHeader header = headerMap.get(key);
            if (header != null) {
                detail.setLotNo(header.getLotNo());
            }
            return detail;
        }).collect(Collectors.toList());
    }

}