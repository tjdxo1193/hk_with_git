package lims.api.tp.enums;

public enum SampleDisposalProgress {
    SAMPLE_STORED("S0270300"),
    REQUEST_DISPOSAL("S0270400"),
    REJECT_DISPOSAL("S0270310"),
    APPROVE_DISPOSAL("S0270500"),
    REQUEST_DISPOSAL_CANCEL("S0270600"),
    REJECT_DISPOSAL_CANCEL("S0270510"),
    APPROVE_DISPOSAL_CANCEL("S0270700");

    private final String code;

    SampleDisposalProgress(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
