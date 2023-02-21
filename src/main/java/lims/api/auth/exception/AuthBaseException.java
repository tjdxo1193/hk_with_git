package lims.api.auth.exception;

import lims.api.common.message.MessageUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthBaseException extends RuntimeException {
    private final HttpStatus status;
    private final String messageCode;
    private final String message;

    public AuthBaseException(HttpStatus status, String messageCode) {
        this.status = status;
        this.messageCode = messageCode;
        this.message = MessageUtil.getMessage(messageCode);
    }
}