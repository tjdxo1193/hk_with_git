package lims.api.integration.enums;

public enum TestStatusProcess {
    TEST_REQUEST(1),
    TEST_PROCEEDING(2),
    TEST_HOLD(3),
    TEST_CANCEL(4),
    TEST_COMPLETE(5),
    INBOUND_CANCEL(6);

    private final int code;

    TestStatusProcess(int code) {
        this.code = code;
    }

    public int getValue() {
        return code;
    }

}