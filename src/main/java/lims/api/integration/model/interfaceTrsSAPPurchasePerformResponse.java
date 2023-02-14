package lims.api.integration.model;

import lims.api.integration.enums.InterfaceResponseStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class interfaceTrsSAPPurchasePerformResponse extends InterfaceInfo {

    private InterfaceResponseStatus xstat = InterfaceResponseStatus.S;
    private String xmsg;
    private List<Info> dataList;

    public InterfaceTrsResponse toTrsResponse() {
        boolean existsError = dataList.stream().anyMatch(info -> InterfaceResponseStatus.E == info.getXstat());
        String message = dataList.stream()
                .peek(info -> log.info(info.toString()))
                .map(Info::getXmsg)
                .distinct()
                .collect(Collectors.joining(", "));

        InterfaceTrsResponse response = new InterfaceTrsResponse();
        response.setXsysid(getXsysid());
        response.setXtid(getXtid());
        response.setXifid(getXifid());
        response.setXdate(getXdate());
        response.setXtime(getXtime());
        response.setXstat(existsError ? InterfaceResponseStatus.E : InterfaceResponseStatus.S);
        response.setXmsg(message);
        return response;
    }

    @Getter
    @Setter
    @ToString
    public static class Info {
        private String werks;
        private String matnr;
        private InterfaceResponseStatus xstat = InterfaceResponseStatus.S;
        private String xmsg;
    }

}