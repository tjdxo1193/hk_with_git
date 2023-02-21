package lims.api.integration.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InterfacePostProcessErrorVO {

    private Integer errorlogId;
    private Integer ifInfoIdx;
    private Integer degree;
    private String exception;
    private String message;
    private String stackTrace;
    private String crtDs;

}