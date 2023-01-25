package lims.api.integration.aop;

import lims.api.common.message.MessageUtil;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.exception.FailedPostProcessException;
import lims.api.integration.service.InterfaceErrorService;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PostProcessorRevExceptionHandlerAspect {

    private final InterfaceErrorService errorService;

    @Around("@annotation(lims.api.integration.annotation.PostProcessorRevExceptionHandler)")
    public Object postProcessorExceptionHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        RevStateful rev = getRevStatefulArgument(joinPoint);

        try {
            return joinPoint.proceed();
        } catch (NullPointerException e) {
            String message = MessageUtil.getMessage("error.notNull");
            Integer errorLogId = errorService.recordByPostProcess(e, rev, message);
            log.error(createFailMessage(rev, errorLogId));
            throw new FailedPostProcessException(e, rev);
        } catch (Exception e) {
            Integer errorLogId = errorService.recordByPostProcess(e, rev);
            log.error(createFailMessage(rev, errorLogId));
            throw new FailedPostProcessException(e, rev);
        }
    }

    private RevStateful getRevStatefulArgument(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof RevStateful) {
                return (RevStateful) arg;
            }
        }

        String message = String.format(
                "[%s] It must be an argument of type RevStateful. %s.%s ",
                ThreadUtil.getCurrentMethodName(),
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName()
        );
        log.error(message);
        throw new IllegalArgumentException(message);
    }

    private String createFailMessage(RevStateful rev, Integer errorLogId) {
        return String.format(
                "Failed post processing. infoIdx: %s, degree: %s, post processor error log id: %s.",
                rev.getIfInfoIdx(),
                rev.getDegree(),
                errorLogId
        );
    }

}