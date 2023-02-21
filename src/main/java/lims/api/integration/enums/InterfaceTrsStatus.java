package lims.api.integration.enums;

public enum InterfaceTrsStatus {
    PENDING("S0170001"),
    SUCCESS("S0170002"),
    FAILED("S0170003");

    private final String code;

    InterfaceTrsStatus(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }

}