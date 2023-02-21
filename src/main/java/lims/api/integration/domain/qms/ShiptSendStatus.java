package lims.api.integration.domain.qms;

public class ShiptSendStatus {

    private final boolean result;

    public ShiptSendStatus(boolean result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return result;
    }

    public boolean isFailed() {
        return !result;
    }

}