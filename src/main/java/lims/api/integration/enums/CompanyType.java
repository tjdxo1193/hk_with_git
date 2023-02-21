package lims.api.integration.enums;

public enum CompanyType {
    KOLMAR("1000");

    private final String code;

    CompanyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}