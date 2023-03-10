package lims.api.integration.enums;

public enum FinalOrderStatus {
    FINISH(1), // 마감
    FINISH_CANCEL(2); // 마감 취소

    final int code;

    FinalOrderStatus(int code) {
        this.code = code;
    }

    public int getValue() {
        return code;
    }

}