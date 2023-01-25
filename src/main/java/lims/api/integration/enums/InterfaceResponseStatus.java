package lims.api.integration.enums;

public enum InterfaceResponseStatus {
    S("success"),
    E("Error");

    private final String message;

    InterfaceResponseStatus(String message) {
        this.message = message;
    }

    public boolean isError() {
        return this == InterfaceResponseStatus.E;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean equals(String code) {
        return this.name().equals(code);
    }

    public static InterfaceResponseStatus of(String name) {
        for (InterfaceResponseStatus status : InterfaceResponseStatus.values()) {
            if (status.name().equals(name)) {
                return status;
            }
        }
        return null;
    }

}