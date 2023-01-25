package lims.api.integration.service.impl.postProcessor.mes;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.MESDao;
import lims.api.integration.dao.MESMasterDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.SRMFinalOrderStatus;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.MESFinalOrderVO;
import lims.api.util.process.SimpleSaveProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MESFinalOrderPostProcessor implements PostProcessor {

    private final MESDao mesDao;
    private final MESMasterDao masterDao;

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int count = 0;
        int degree = rev.getDegree();

        count += new SimpleSaveProcess<MESFinalOrderVO>().forEachSave(
            mesDao.findFinalOrderByDegree(degree),
            masterDao.findFinalOrder(),
            vo -> {
                /**
                 * '마감 상태'는 SRM에서만 보내줍니다.
                 * MES에서는 '마감 상태'라는 값이 없지만 SRM과 동일하게 처리하기 위해 
                 * 마스터 테이블에 '마감' 값을 고정적으로 넣어줍니다
                 */
                vo.setStatus(SRMFinalOrderStatus.FINISH.getValue());
                return masterDao.createFinalOrder(vo);
            }
        ).getTotalCount();

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }

    }
}