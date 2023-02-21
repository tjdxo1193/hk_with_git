package lims.api.kp.enums;

public enum ReasonForAddSample {
    OutOfSpecification("U0250001"),
    OTHER("U0250002");

    private final String code;

    ReasonForAddSample(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
