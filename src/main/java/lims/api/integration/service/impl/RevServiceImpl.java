package lims.api.integration.service.impl;

import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.service.RevService;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class RevServiceImpl implements RevService {

    private final DataSourceTransactionManager transactionManager;

    @Override
    public void execute(RevInterface revInterface, Integer degree, Function<Integer, Integer> mainProcess, Consumer<Integer> postProcessorRunnable) {
        int count = 0;

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            count += mainProcess.apply(degree);

            if (count == 0) {
                throw new IntegrationNoSavedException();
            }

            transactionManager.commit(status);

            if (postProcessorRunnable != null) {
                postProcessorRunnable.accept(degree);
            }
        } catch (Exception e) {
            log.error(
                    "[{}] Failed receive integration interface data. interface name: {}, interface id: {}, degree: {}, message: {}",
                    ThreadUtil.getCurrentMethodName(),
                    revInterface,
                    revInterface.getId(),
                    degree,
                    e.getMessage()
            );
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public void execute(RevInterface revInterface, Integer degree, Function<Integer, Integer> mainProcess) {
        execute(revInterface, degree, mainProcess, null);
    }

}