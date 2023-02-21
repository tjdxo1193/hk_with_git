package lims.api.integration.model;

import lims.api.integration.enums.InterfaceInfoDiv;
import lims.api.integration.enums.InterfaceResponseStatus;
import lims.api.integration.vo.IfInfoVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InterfaceInfo {

    private String xsysid;
    private String xtid;
    private String xifid;
    private String xdate;
    private String xtime;

    public IfInfoVO getRevInfoVO() {
        return IfInfoVO.builder()
                .xsysid(xsysid)
                .xtid(xtid)
                .xifid(xifid)
                .xdate(xdate)
                .xtime(xtime)
                .xstat(getXstat())
                .xmsg(getXmsg())
                .infoDiv(InterfaceInfoDiv.REV)
                .build();
    }

    public IfInfoVO getTrsInfoVO() {
        return IfInfoVO.builder()
                .xtid(xtid)
                .xdate(xdate)
                .xtime(xtime)
                .xstat(getXstat())
                .xmsg(getXmsg())
                .infoDiv(InterfaceInfoDiv.TRS)
                .build();
    }

    protected InterfaceResponseStatus getXstat() {
        return null;
    }

    protected String getXmsg() {
        return null;
    }

}