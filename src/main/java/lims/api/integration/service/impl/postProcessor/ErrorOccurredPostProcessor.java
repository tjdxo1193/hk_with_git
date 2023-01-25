package lims.api.integration.service.impl.postProcessor;

import lims.api.integration.dao.InterfaceErrorDao;
import lims.api.integration.dao.InterfaceInfoDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.vo.IfInfoVO;
import lims.api.integration.vo.InterfacePostProcessErrorVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ErrorOccurredPostProcessor {

    private final InterfaceInfoDao infoDao;
    private final InterfaceErrorDao errorDao;
    private final InterfacePostProcessorMap postProcessorMap;

    public void retry(Integer postProcessErrorLogId) {
        InterfacePostProcessErrorVO error = errorDao.findPostProcessById(postProcessErrorLogId);
        Integer infoIdx = error.getIfInfoIdx();
        Integer degree = error.getDegree();

        PostProcessor processor = getPostProcessor(infoIdx);

        if (processor != null) {
            processor.execute(new RevStateful(degree, infoIdx));
        }
    }

    private PostProcessor getPostProcessor(Integer infoIdx) {
        String interfaceId = getInterfaceId(infoIdx);
        RevInterface processorDiv = RevInterface.of(interfaceId);
        return postProcessorMap.get(processorDiv);
    }

    private String getInterfaceId(Integer infoIdx) {
        IfInfoVO info = infoDao.findById(infoIdx);
        return info.getXifid();
    }

}