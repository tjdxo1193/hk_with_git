package lims.api.integration.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InterfaceErrorVO {

    private Integer errorlogId;
    private Integer ifInfoIdx;
    private String exception;
    private String message;
    private String stackTrace;
    private String crtDs;

}