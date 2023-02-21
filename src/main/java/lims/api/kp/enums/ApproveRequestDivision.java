package lims.api.kp.enums;

public enum ApproveRequestDivision {
    ADD_SAMPLE("S0050021");

    private final String code;

    ApproveRequestDivision(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
