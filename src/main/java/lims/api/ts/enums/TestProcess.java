package lims.api.ts.enums;

import lombok.Getter;

@Getter
public enum TestProcess {
    TEST_REQUEST("S0130100"),
    TEST_RECEIPT("S0130200"),
    TEST_COLLECTION("S0130300"),
    TEST_INSTRUCTION("S0130400"),
    TEST_ASSIGN("S0130500"),
    TEST_RESULT_INPUT("S0130600"),
    TEST_REVIEW_REJECT("S0130610"),
    TEST_REVIEW("S0130700"),
    TEST_APPROVAL_REJECT("S0130710"),
    TEST_APPROVED("S0130800"),
    TEST_FINISH("S0130900"),
    TEST_CANCEL("S0131000"),
    TEST_RESULT_INPUT_WAITING("S0140100"),
    TEST_RESULT_INPUT_COMPLETED("S0140200");

    private final String processCode;

    TestProcess(String code) {
        processCode = code;
    }

    public boolean equals(TestProcess process) {
        return this == process;
    }

}
