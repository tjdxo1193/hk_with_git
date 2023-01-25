package lims.api.auth.exception;

import lims.api.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends AuthBaseException {

    public UnauthenticatedException() {
        this(HttpStatus.UNAUTHORIZED, "auth.error.unauthenticated");
    }

    public UnauthenticatedException(String messageCode) {
        this(HttpStatus.UNAUTHORIZED, messageCode);
    }

    public UnauthenticatedException(HttpStatus status, String messageCode) {
        super(status, messageCode);
    }
}