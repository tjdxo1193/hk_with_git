package lims.api.tp.enums;

public enum SampleUsageProgress {
    TEMP_SAVE("S0280100"),
    REQUEST_USE("S0280200"),
    REJECT_USE("S0280110"),
    APPROVE_USE("S0280300"),
    REQUEST_CANCEL_USE("S0280400"),
    REJECT_CANCEL_USE("S0280310"),
    APPROVE_USE_CANCEL("S0280500");

    private final String code;

    SampleUsageProgress(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
