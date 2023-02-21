package lims.api.integration.domain.eai;

import lims.api.integration.enums.InterfaceResponseStatus;
import lims.api.integration.enums.InterfaceTrsStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public class TrsResult {
    @Getter
    Integer degree;
    @Getter
    Integer ifInfoIdx;
    @Getter
    LocalDateTime trsDate;
    InterfaceTrsStatus trsStatus;
    InterfaceResponseStatus responseStatus;

    public boolean isSuccess() {
        return InterfaceTrsStatus.SUCCESS == trsStatus && InterfaceResponseStatus.S == responseStatus;
    }

    public boolean isFail() {
        return InterfaceTrsStatus.FAILED == trsStatus || InterfaceResponseStatus.E == responseStatus;
    }

}