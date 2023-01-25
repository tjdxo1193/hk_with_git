package lims.api.integration.vo;

import lims.api.integration.enums.InterfaceInfoDiv;
import lims.api.integration.enums.InterfaceResponseStatus;
import lims.api.integration.enums.InterfaceSendType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IfInfoVO {

    private Integer idx;
    private String xtid;
    private String xifid;
    private String xsysid;
    private String xdate;
    private String xtime;
    private InterfaceInfoDiv infoDiv;
    private InterfaceResponseStatus xstat;
    private String xmsg;
    private InterfaceSendType ifType;
    private Integer errorLogId;

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

}