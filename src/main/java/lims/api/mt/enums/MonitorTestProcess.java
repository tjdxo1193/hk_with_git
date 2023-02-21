package lims.api.mt.enums;

import lombok.Getter;

@Getter
public enum MonitorTestProcess {
    MONITOR_TEST_REQUEST("S0130100"),
    MONITOR_TEST_RECEIPT("S0130200"),
    MONITOR_TEST_INSTRUCTION("S0130400"),
    MONITOR_TEST_ASSIGN("S0130500"),
    MONITOR_TEST_RESULT_INPUT("S0130600"),
    MONITOR_TEST_REVIEW_REJECT("S0130610"),
    MONITOR_TEST_REVIEW("S0130700"),
    MONITOR_TEST_APPROVAL_REJECT("S0130710"),
    MONITOR_TEST_APPROVED("S0130800"),
    MONITOR_TEST_FINISH("S0130900"),
    MONITOR_TEST_CANCEL("S0131000"),
    MONITOR_TEST_RESULT_INPUT_WAITING("S0140100"),
    MONITOR_TEST_RESULT_INPUT_COMPLETED("S0140200");

    private final String processCode;

    MonitorTestProcess(String code) {
        processCode = code;
    }

    public boolean equals(MonitorTestProcess process) {
        return this == process;
    }
}
