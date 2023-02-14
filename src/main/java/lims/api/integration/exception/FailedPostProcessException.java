package lims.api.integration.exception;

import lims.api.integration.domain.eai.RevStateful;
import lombok.Getter;

// TODO DELETE
@Getter
public class FailedPostProcessException extends RuntimeException {

    private final RevStateful revStateful;

    public FailedPostProcessException(Throwable cause, RevStateful revStateful) {
        super(cause);
        this.revStateful = revStateful;
    }
}