package lims.api.integration.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lims.api.integration.enums.InterfaceResponseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MESResponse {

    private InterfaceResponseStatus xstat;
    private String xmsg;
    private String xdate;
    private String xtime;

    public static MESResponse toSuccessResponse(InterfaceInfo param) {
        return MESResponse.builder()
                .xstat(InterfaceResponseStatus.S)
                .xmsg(InterfaceResponseStatus.S.getMessage())
                .xdate(param.getXdate())
                .xtime(param.getXtime())
                .build();
    }

    public static MESResponse toErrorResponse(InterfaceInfo param, String message) {
        return MESResponse.builder()
                .xstat(InterfaceResponseStatus.E)
                .xmsg(message)
                .xdate(param.getXdate())
                .xtime(param.getXtime())
                .build();
    }

}