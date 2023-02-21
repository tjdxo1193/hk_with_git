package lims.api.integration.advice;

import lims.api.integration.controller.*;
import lims.api.integration.exception.FailedPostProcessException;
import lims.api.integration.service.InterfaceErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackageClasses = {
        ELNController.class,
        MESController.class,
        QMSController.class,
        SAPController.class,
        SRMController.class
})
@RequiredArgsConstructor
public class InterfaceControllerAdvice {

    private final InterfaceErrorService errorService;

    // TODO DELETE
    @ExceptionHandler(FailedPostProcessException.class)
    public void failedPostProcessExceptionHandler(FailedPostProcessException e) {
        errorService.recordByPostProcess(e, e.getRevStateful(), e.getMessage());
        throw e;
    }

    @ExceptionHandler
    public void exceptionHandler(Exception e) throws Exception {
        errorService.record(e);
        throw e;
    }

}