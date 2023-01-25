package lims.api.integration.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lims.api.integration.enums.InterfaceResponseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SAPResponse {

    private String guid;
    private String xtid;
    private String xifid;
    private String xdate;
    private String xtime;
    private InterfaceResponseStatus xstat;
    private String xmsg;

    public static SAPResponse toEmptyResponse() {
        return SAPResponse.builder().build();
    }

    public static SAPResponse toSuccessResponse(InterfaceInfo param) {
        return SAPResponse.builder()
                .xstat(InterfaceResponseStatus.S)
                .xmsg(InterfaceResponseStatus.S.getMessage())
                .xdate(param.getXdate())
                .xtime(param.getXtime())
                .build();
    }

    public static SAPResponse toErrorResponse(InterfaceInfo param, String message) {
        return SAPResponse.builder()
                .xstat(InterfaceResponseStatus.E)
                .xmsg(message)
                .xdate(param.getXdate())
                .xtime(param.getXtime())
                .build();
    }

    public static SAPResponse toErrorResponse(InterfaceInfo param, Exception e) {
        return toErrorResponse(param, e.getMessage());
    }

}