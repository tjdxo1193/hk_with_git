package lims.api.integration.domain.eai;

public abstract class InterfaceSystem {

    private final InterfaceSystemProperty property;

    public InterfaceSystem(InterfaceSystemProperty property) {
        this.property = property;
    }

    public String getEAIProtocol() {
        return property.getEaiProtocol();
    }

    public String getEAIHost() {
        return property.getEaiHost();
    }

    public String getProtocol() {
        return property.getProtocol();
    }

    public String getHost() {
        return property.getHost();
    }

}