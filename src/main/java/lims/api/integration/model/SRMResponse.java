package lims.api.integration.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lims.api.integration.enums.InterfaceResponseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SRMResponse {

    private InterfaceResponseStatus xstat;
    private String xmsg;
    private String xdate;
    private String xtime;
    private String ifId;
    private InterfaceResponseStatus ifStatus;
    private String ifMessage;

    public static SRMResponse toSuccessResponse(InterfaceInfo param) {
        return SRMResponse.builder()
                .xstat(InterfaceResponseStatus.S)
                .xmsg(InterfaceResponseStatus.S.getMessage())
                .xdate(param.getXdate())
                .xtime(param.getXtime())
                .ifId(param.getXifid())
                .ifStatus(InterfaceResponseStatus.S)
                .ifMessage(InterfaceResponseStatus.S.getMessage())
                .build();
    }

    public static SRMResponse toErrorResponse(InterfaceInfo param, String message) {
        return SRMResponse.builder()
                .xstat(InterfaceResponseStatus.E)
                .xmsg(message)
                .xdate(param.getXdate())
                .xtime(param.getXtime())
                .ifId(param.getXifid())
                .ifStatus(InterfaceResponseStatus.E)
                .ifMessage(message)
                .build();
    }

    public static SRMResponse toErrorResponse(InterfaceInfo param, Exception e) {
        return toErrorResponse(param, e.getMessage());
    }

}