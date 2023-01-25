package lims.api.ms.enums;

public enum ItemManage {
    TEMP_SAVE("S0080100"),
    RETURN_REVIEW("S0080110"),
    REQUEST_REVIEW("S0080200"),
    RETURN_APPROVE("S0080210"),
    REQUEST_APPROVE("S0080300"),
    APRROVED("S0080400");

    private final String code;

    ItemManage(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

}