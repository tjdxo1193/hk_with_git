package lims.api.integration.enums;

public enum MaterialLang {
    KO("3"),
    EN("E"),
    CN("1");

    private final String code;

    MaterialLang(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }
}