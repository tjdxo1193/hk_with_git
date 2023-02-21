package lims.api.tp.enums;

public enum SampleDivision {
    SAMPLE_FOR_TEST("S0260001"),
    SAMPLE_FOR_OTHER("S0260002"),
    SAMPLE_FOR_KEEP("S0260003"),
    ADDED_SAMPLE("S0260004");

    private final String code;

    SampleDivision(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
