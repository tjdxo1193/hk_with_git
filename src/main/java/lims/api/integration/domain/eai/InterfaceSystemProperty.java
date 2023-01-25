package lims.api.integration.domain.eai;

import lombok.Getter;

@Getter
public class InterfaceSystemProperty {
    private final String eaiProtocol;
    private final String eaiHost;
    private final String protocol;
    private final String host;

    public InterfaceSystemProperty(String eaiProtocol, String eaiHost, String protocol, String host) {
        this.eaiProtocol = eaiProtocol;
        this.eaiHost = eaiHost;
        this.protocol = protocol;
        this.host = host;
    }

}