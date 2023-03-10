package lims.api.sy.vo;

import lims.api.integration.enums.InterfaceSendType;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.enums.TrsInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterfaceMasterVO {
    private String sendDirection;
    private String id;
    private String path;
    private String interfaceDiv;
    private InterfaceSystemType system;
    private String sendType;
    private String desc;

    public InterfaceMasterVO() {}

    public InterfaceMasterVO(RevInterface rev) {
        this.sendDirection = "수신";
        this.id = rev.getId();
        this.system = rev.getSystemType();
        this.sendType = getSendTypeString(rev.getSendType());
        this.desc = rev.getDesc();
        this.interfaceDiv = getInterfaceDivString(rev.getId());
    }

    public InterfaceMasterVO(TrsInterface trs) {
        this.sendDirection = "송신";
        this.id = trs.getId();
        this.system = trs.getSystemType();
        this.sendType = getSendTypeString(trs.getSendType());
        this.path = trs.getServicePath();
        this.desc = trs.getDesc();
        this.interfaceDiv = getInterfaceDivString(trs.getId());
    }

    private String getSendTypeString(InterfaceSendType sendType) {
        return sendType == InterfaceSendType.SYNC ? "동기" : "비동기";
    }

    private String getInterfaceDivString(String interfaceId) {
        return interfaceId == null ? "직접" : "EAI";
    }
}