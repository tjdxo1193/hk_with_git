package lims.api.ms.enums;

public enum UserDelegate {
    DELEGATE("S0160001"),
    STAY("S0160002");

    private final String code;

    UserDelegate(String code) {
        this.code = code;
    }

    /**
     * @implNote to bind mybatis query
     */
    public String getValue() {
        return code;
    }

    public boolean isDeletegate() {
        return this == UserDelegate.DELEGATE;
    }

    public boolean isStay() {
        return this == UserDelegate.STAY;
    }
}