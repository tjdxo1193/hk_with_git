package lims.api.common.advice;

import lims.api.auth.exception.UnauthenticatedAccessException;
import lims.api.auth.exception.UnauthenticatedException;
import lims.api.common.model.ErrorResponse;
import lims.api.common.service.impl.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthenticationExceptionAdvice {

    private final ErrorService errorService;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unauthenticatedExceptionHandler(UnauthenticatedException e) {
        return errorService.response(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unauthenticatedAccessExceptionHandler(UnauthenticatedAccessException e) {
        return errorService.record(e).response(e.getMessage(), e.getStatus());
    }

}