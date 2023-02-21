package lims.api.tp.enums;

public enum ApproveRequestDivision {
    DISPOSE_SAMPLE("S0050017"),
    USE_SAMPLE("S0050018");

    private final String code;

    ApproveRequestDivision(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
