package lims.api.integration.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lims.api.integration.enums.InterfaceResponseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ELNResponse {

    private InterfaceResponseStatus xstat;
    private String xmsg;

    public static ELNResponse toSuccessResponse() {
        return ELNResponse.builder()
                .xstat(InterfaceResponseStatus.S)
                .xmsg(InterfaceResponseStatus.S.getMessage())
                .build();
    }

    public static ELNResponse toErrorResponse(Exception e, String message) {
        return ELNResponse.builder()
                .xstat(InterfaceResponseStatus.E)
                .xmsg(message)
                .build();
    }

}