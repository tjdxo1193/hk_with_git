package lims.api.auth.enums;

public enum CookieSameSite {
    UNSET("Unset"),

    /**
     * Cookie is always sent in cross-site requests.
     */
    NONE("None"),

    /**
     * Cookie is only sent on same-site requests and cross-site top level navigation GET requests
     */
    LAX("Lax"),

    /**
     * Prevents the cookie from being sent by the browser in all cross-site requests
     */
    STRICT("Strict");

    private final String value;

    CookieSameSite(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}