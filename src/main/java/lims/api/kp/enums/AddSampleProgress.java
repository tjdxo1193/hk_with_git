package lims.api.kp.enums;

public enum AddSampleProgress {
    TEMP_SAVE("S0320100"),
    REQUEST_ADD_SAMPLE("S0320200"),
    REJECT_ADD_SAMPLE("S0320110"),
    APPROVE_ADD_SAMPLE("S0320300");

    private final String code;

    AddSampleProgress(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
