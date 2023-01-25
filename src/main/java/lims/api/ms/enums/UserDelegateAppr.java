package lims.api.ms.enums;

public enum UserDelegateAppr {
    TEMP_SAVE("S0150100"),
    RETURN("S0150110"),
    REQUEST("S0150200"),
    COMPLETE("S0150300");

    private final String code;

    UserDelegateAppr(String code) {
        this.code = code;
    }

    /**
     * @implNote to bind mybatis query
     */
    public String getValue() {
        return code;
    }

    public boolean isTempSave() {
        return this == UserDelegateAppr.TEMP_SAVE;
    }

    public boolean isReturn() {
        return this == UserDelegateAppr.RETURN;
    }

    public boolean isRequest() {
        return this == UserDelegateAppr.REQUEST;
    }

    public boolean isComplete() {
        return this == UserDelegateAppr.COMPLETE;
    }

}