package lims.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MismatchedPasswordConditionException extends RuntimeException{

    private final HttpStatus status;
    private final String message;

    public MismatchedPasswordConditionException(String message) {
        this.status = HttpStatus.NON_AUTHORITATIVE_INFORMATION;
        this.message = message;
    }

}
