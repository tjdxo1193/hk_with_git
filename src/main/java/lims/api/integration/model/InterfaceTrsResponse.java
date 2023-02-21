package lims.api.integration.model;

import lims.api.integration.domain.eai.ResponseMultiData;
import lims.api.integration.enums.InterfaceResponseStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InterfaceTrsResponse extends InterfaceInfo {

    private InterfaceResponseStatus xstat = InterfaceResponseStatus.S;
    private String xmsg;
    private ResponseMultiData dataList;

}