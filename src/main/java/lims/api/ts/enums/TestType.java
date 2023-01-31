package lims.api.ts.enums;

public enum TestType {
    PURCHASE_ORDER("S0230001"),
    MANUFACTURE_ORDER("S0230002"),
    MANUAL("S0230003"),
    STABILITY("S0230004"),
    RETRY("S0230005"),
    VALIDATION("S0230006");

    private final String code;

    TestType(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

    public static boolean bySAP(String code) {
        return PURCHASE_ORDER.equals(code) ||
               MANUFACTURE_ORDER.equals(code) ||
               MANUAL.equals(code);
    }
}